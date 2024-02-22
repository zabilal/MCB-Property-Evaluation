CREATE TABLE `tbl_file_category`  -- this is similar to department table
(
    `category_id`   int(11) NOT NULL,
    `category_name` varchar(30)  NOT NULL,
    `description`   varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_file_management` --this is similar to correspondence table
(
    `file_id`       int(11) NOT NULL,
    `file_name`     varchar(30)  NOT NULL,
    `category_id`   int(11) NOT NULL,
    `description`   varchar(100) NOT NULL,
    `tags`          text         NOT NULL,
    `file_upload`   text         NOT NULL,
    `file_type`     int(1) NOT NULL,
    `uploaded_by`   int(11) NOT NULL,
    `date_uploaded` date         NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tbl_user`   -- this is similar to user table
(
    `user_id`     int(11) NOT NULL,
    `full_name`   varchar(100) NOT NULL,
    `username`    varchar(30)  NOT NULL,
    `password`    text         NOT NULL,
    `designation` varchar(30)  NOT NULL,
    `code_name`   varchar(30)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `tbl_commit`
(
    `commit_id`      int(11) NOT NULL,
    `upload_file`    text         NOT NULL,
    `file_id`        int(11) NOT NULL,
    `commit_remarks` varchar(100) NOT NULL,
    `commited_by`    int(11) NOT NULL,
    `date`           date         NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `tbl_user_log`
(
    `log_id`      int(11) NOT NULL,
    `user_id`     int(11) NOT NULL,
    `log_type`    int(1) NOT NULL,
    `file_path`   text NOT NULL,
    `commit_time` time NOT NULL,
    `commit_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
