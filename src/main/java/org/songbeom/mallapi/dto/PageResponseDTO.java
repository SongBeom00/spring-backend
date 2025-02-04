package org.songbeom.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
public class PageResponseDTO<E> { //페이징 결과물 -> (목록 데이터 (DTO)


    private List<E> dtoList; //dto 의 목록 데이터

    private List<Integer> pageNumList;


    private PageRequestDTO pageRequestDTO;


    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;


    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList,PageRequestDTO pageRequestDTO,long total){
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)total;

        //끝페이지 end
        int end = (int) Math.ceil(pageRequestDTO.getPage() / 10.0) * 10;

        //시작페이지 start ->  1~10 11~20 21~30 ...
        int start = end - 9;

        //진짜 마지막 page
        int last = (int) Math.ceil(totalCount/(double)pageRequestDTO.getSize());

        end = Math.min(end, last); //end 가 last 보다 크면 last 로 변경

        this.prev = start > 1;

        this.next = totalCount > end * pageRequestDTO.getSize();

        this.pageNumList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 1;

        this.nextPage = next ? end + 1 : 0;




    }

}
