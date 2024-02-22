package gloomme.crater365.correspondence.service;

import gloomme.crater365.correspondence.client.AuthClient;
import gloomme.crater365.correspondence.dto.CorrespondenceDTO;
import gloomme.crater365.correspondence.dto.ReviewDTO;
import gloomme.crater365.correspondence.entity.Reviews;
import gloomme.crater365.correspondence.entity.Correspondence;
import gloomme.crater365.correspondence.entity.CorrespondenceLogs;
import gloomme.crater365.correspondence.enums.LogTypes;
import gloomme.crater365.correspondence.enums.Stage;
import gloomme.crater365.correspondence.exception.CorrespondenceDataException;
import gloomme.crater365.correspondence.exception.CorrespondenceNotFoundException;
import gloomme.crater365.correspondence.repository.ReviewRepository;
import gloomme.crater365.correspondence.repository.CorrespondenceLogsRepository;
import gloomme.crater365.correspondence.repository.CorrespondenceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class CorrespondenceService {

    private CorrespondenceRepository correspondenceRepository;
    private ReviewRepository reviewRepository;
    private CorrespondenceLogsRepository correspondenceLogsRepository;
    private AuthClient authClient;

    public Map<String, Object> createNewCorrespondence(CorrespondenceDTO correspondenceDTO) {
        log.info("Creating a new Correspondence");
        if (correspondenceDTO.getTitle().isEmpty()) {
            throw new CorrespondenceDataException("Invalid Title, title cannot be empty");
        }
        Optional<Correspondence> correspondenceByTitle = correspondenceRepository.findByOrganizationCraterIdAndTitle(correspondenceDTO.getOrganizationCraterId(), correspondenceDTO.getTitle());
        if (correspondenceByTitle.isPresent()) {
            throw new CorrespondenceDataException("Correspondence title already exist");
        }
        Correspondence correspondence = new Correspondence();
        BeanUtils.copyProperties(correspondenceDTO, correspondence);
        correspondence.setCraterId(UUID.randomUUID().toString());

        Correspondence newCorrespondence = correspondenceRepository.save(correspondence);
        log.info("NEW Correspondence created :: {}", newCorrespondence);

        //Create a log of the Correspondence
        CorrespondenceLogs correspondenceLogs = new CorrespondenceLogs();
        correspondenceLogs.setCraterId(UUID.randomUUID().toString());
        correspondenceLogs.setLogType(LogTypes.ADD);
        correspondenceLogs.setCorrespondence(newCorrespondence);
        correspondenceLogs.setFilePath(newCorrespondence.getFileUrl());
        correspondenceLogs.setUserCraterId(newCorrespondence.getCreatedBy());
        correspondenceLogs.setOrganizationCraterId(newCorrespondence.getOrganizationCraterId());
        correspondenceLogs.setCreatedBy(newCorrespondence.getInitiatorId());
        CorrespondenceLogs saveLogs = correspondenceLogsRepository.save(correspondenceLogs);

        CorrespondenceDTO responseDTO = new CorrespondenceDTO();
        BeanUtils.copyProperties(newCorrespondence, responseDTO);

        List<CorrespondenceLogs> savedLogList = new ArrayList<>();
        savedLogList.add(saveLogs);

        Map<String, Object> response = new HashMap<>();
        response.put("correspondence", responseDTO);
        response.put("logs", savedLogList);

        return response;

    }

    public Map<String, Object> getAllByOrganizationsCraterId(int page, int size, String organizationCraterId) {
        Pageable paging = PageRequest.of(page, size);
        Page<Correspondence> result = correspondenceRepository.findByOrganizationCraterId(organizationCraterId, paging);

        List<Correspondence> correspondenceList = result.toList();
        List<CorrespondenceDTO> responseList = new ArrayList<>();

        correspondenceList.forEach(correspondence -> {
            CorrespondenceDTO dto = new CorrespondenceDTO();
            BeanUtils.copyProperties(correspondence, dto);
            //        get all logs for each correspondence
            List<CorrespondenceLogs> logsByCorrespondence = correspondenceLogsRepository.findAllByCorrespondence(correspondence);
            dto.setLogs(logsByCorrespondence);
            //        get all reviews by correspondence
            List<Reviews> reviewsList = reviewRepository.findAllByCorrespondence(correspondence);
            dto.setReviews(reviewsList);
            responseList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("correspondences", responseList);
        response.put("currentPage", result.getNumber());
        response.put("totalItems", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());

        return response;
    }

    public CorrespondenceDTO getOneCorrespondenceByOrganization(String organizationCraterId, String craterId) {
        Optional<Correspondence> optionalCorrespondence = correspondenceRepository.findByOrganizationCraterIdAndCraterId(organizationCraterId, craterId);
        if (optionalCorrespondence.isEmpty()) {
            log.info("Correspondence with id {} does not exist", craterId);
            throw new CorrespondenceNotFoundException("Correspondence not found");
        }
        Correspondence correspondence = optionalCorrespondence.get();

        var dto = new CorrespondenceDTO();
        BeanUtils.copyProperties(correspondence, dto);
        //        get all logs for each correspondence
        List<CorrespondenceLogs> logsByCorrespondence = correspondenceLogsRepository.findAllByCorrespondence(correspondence);
        dto.setLogs(logsByCorrespondence);
        //        get all reviews by correspondence
        List<Reviews> reviewsList = reviewRepository.findAllByCorrespondence(correspondence);
        dto.setReviews(reviewsList);

        return dto;
    }

    private Correspondence getCorrespondenceByTitle(String title) {
        Optional<Correspondence> optionalCorrespondence = correspondenceRepository.findCorrespondenceByTitle(title);
        if (optionalCorrespondence.isEmpty()) {
            log.info("Correspondence with title {} does not exist", title);
            throw new CorrespondenceDataException("Correspondence does not exist");
        }
        return optionalCorrespondence.get();
    }

    public Map<String, Object> processCorrespondence(CorrespondenceDTO correspondenceDTO) {
        Optional<Correspondence> correspondenceByCraterId = correspondenceRepository.findCorrespondenceByCraterId(correspondenceDTO.getCraterId());
        if (correspondenceByCraterId.isEmpty()) {
            throw new CorrespondenceNotFoundException("No Correspondence with ID : " + correspondenceDTO.getCraterId());
        }
        Correspondence correspondence = correspondenceByCraterId.get();

        Stage stage = correspondenceDTO.getStage();

        BeanUtils.copyProperties(correspondenceDTO, correspondence);

        if (stage == Stage.REVIEW) {
            correspondence.setReviewerId(correspondenceDTO.getReviewerId());
        }

        if (stage == Stage.BASIC_APPROVAL) {
            correspondence.setBapproverId(correspondenceDTO.getBapproverId());
        }

        if (stage == Stage.FINAL_APPROVAL) {
            correspondence.setFapproverId(correspondence.getFapproverId());
        }

        if (stage == Stage.FURTHER_ACTION) {
            correspondence.setFapproverId(correspondenceDTO.getInitiatorId());
        }

        Correspondence saveCorrespondence = correspondenceRepository.save(correspondence);

        CorrespondenceLogs correspondenceLogs = new CorrespondenceLogs();
        correspondenceLogs.setLogType(LogTypes.UPDATE);
        correspondenceLogs.setFilePath(saveCorrespondence.getFileUrl());
        correspondenceLogs.setUserCraterId(saveCorrespondence.getCreatedBy());
        correspondenceLogs.setOrganizationCraterId(saveCorrespondence.getOrganizationCraterId());
        correspondenceLogs.setCreatedBy(saveCorrespondence.getInitiatorId());
        CorrespondenceLogs saveLogs = correspondenceLogsRepository.save(correspondenceLogs);

        BeanUtils.copyProperties(saveCorrespondence, correspondenceDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("correspondence", correspondenceDTO);
        response.put("logs", List.of(saveLogs));

        return response;

    }

    public CorrespondenceDTO updateCorrespondence(CorrespondenceDTO dto) {
        if (Objects.isNull(dto.getCraterId())) {
            throw new CorrespondenceDataException("Correspondence data is empty");
        }

        Optional<Correspondence> optionalCorrespondence = correspondenceRepository.findCorrespondenceByCraterId(dto.getCraterId());
        if (!optionalCorrespondence.isPresent()) {
            throw new CorrespondenceNotFoundException("Correspondence not Found");
        }
        Correspondence correspondence = optionalCorrespondence.get();
        BeanUtils.copyProperties(dto, correspondence);

        Correspondence saveCorrespondence = correspondenceRepository.save(correspondence);

        CorrespondenceLogs logs = new CorrespondenceLogs();
        logs.setCorrespondence(correspondence);
        logs.setLogType(LogTypes.UPDATE);
        logs.setFilePath(correspondence.getFileUrl());
        logs.setOrganizationCraterId(correspondence.getOrganizationCraterId());
        logs.setUserCraterId(dto.getUserCraterId());
        correspondenceLogsRepository.save(logs);

        BeanUtils.copyProperties(saveCorrespondence, dto);

        return dto;
    }

    public Map<String, Object> addReviews(ReviewDTO dto) {
        if (Objects.isNull(dto.getCorrespondenceId())) {
            throw new CorrespondenceDataException("Review data is missing");
        }

        Optional<Correspondence> correspondenceByCraterId = correspondenceRepository.findCorrespondenceByCraterId(dto.getCorrespondenceId());
        if (correspondenceByCraterId.isEmpty()) {
            throw new CorrespondenceNotFoundException("Correspondence not found");
        }

        Correspondence correspondence = correspondenceByCraterId.get();

        Reviews reviews = new Reviews();

        BeanUtils.copyProperties(dto, reviews);
        reviews.setCraterId(UUID.randomUUID().toString());
        reviews.setCorrespondence(correspondence);
        reviews.setCreatedBy(dto.getReviewedBy());
        reviews.setFileUrl(correspondence.getFileUrl());
        reviewRepository.save(reviews);

        CorrespondenceLogs logs = new CorrespondenceLogs();
        logs.setCraterId(UUID.randomUUID().toString());
        logs.setCorrespondence(correspondence);
        logs.setCreatedBy(dto.getReviewedBy());
        logs.setLogType(LogTypes.REVIEW);
        logs.setOrganizationCraterId(dto.getOrganizationCraterId());
        correspondenceLogsRepository.save(logs);

        List<CorrespondenceLogs> logsByCorrespondence = correspondenceLogsRepository.findAllByCorrespondence(correspondence);
        List<Reviews> reviewsList = reviewRepository.findAllByCorrespondence(correspondence);

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewsList);
        response.put("logs", logsByCorrespondence);

        return response;
    }
}
