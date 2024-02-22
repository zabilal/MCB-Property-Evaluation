package com.mcbproperty.authservice.util;

import java.io.Serializable;
import java.util.Optional;

public class Result<T> implements Serializable {
    private Optional<T> value;
    private Optional<String> error;

    private Result(T value, String error) {
        this.value = Optional.ofNullable(value);
        this.error = Optional.ofNullable(error);
    }

    /** This is the unit method */
    public static <U> Result<U> ok(U value) {
        return new Result<>(value, null);
    }

    public static <U> Result<U> error(String error) {
        return new Result<>(null, error);
    }

    /** This is the bind method */
//    public<U> Result<U> flatMap(Function<T, Result<U>> mapper) {
//
//        if(this.isError()) {
//            return Result.error(this.error());
//        }
//
//        return mapper.apply(value.get());
//    }

    public boolean isError() {
        return error.isPresent();
    }

    public T getValue() {
        return value.get();
    }

    public String getError() {
        return error.get();
    }
}


//sample use case without flatmaps
//    /**
//     * Read ints from two files, Adjust the first one and then calculate average.
//     * Returns a Result wrapping the positive outcome or any error.
//     */
//    public Result<Double> businessOperation(String fileName, String fileName2) {
//        Result<Integer> val1 = readIntFromFile(fileName);
//
//        if (val1.isError()) {
//            return Result.error(val1.getError());
//        }
//
//        Result<Integer> adjusted1 = adjustValue(val1.getValue());
//
//        if (adjusted1.isError()) {
//            return Result.error(adjusted1.getError());
//        }
//
//        Result<Integer> val2 = readIntFromFile(fileName2);
//
//        if (val2.isError()) {
//            return Result.error(val2.getError());
//        }
//
//        Double average = calculateAverage(adjusted1.getValue(), val2.getValue());
//        return Result.ok(average);
//    }


//same use case but with flatmap
//    /**
//     * Read ints from two files, Adjust the first one and then calculate average.
//     * Returns a Result wrapping the positive outcome or any error.
//     */
//    public Result<Double> businessOperation(String fileName, String fileName2) {
//
//        Result<Integer> adjustedValue = readIntFromFile(fileName)
//                .flatMap(this::adjustValue)
//
//        return adjustedValue.flatMap( val1 ->
//                readIntFromFile(fileName2).flatMap( val2 ->
//                        Result.ok(calculateAverage(val1, val2))
//                ));
//    }