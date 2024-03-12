package com.warungposbespring.warungposbe.utils;

public class PaginationUtils {

    public static int getPagination(int totalItems, int itemPerPage){
        return (int) Math.ceil((double) totalItems/ itemPerPage);
    };

}
