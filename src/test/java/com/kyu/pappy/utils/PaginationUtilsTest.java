package com.kyu.pappy.utils;

import com.kyu.pappy.model.pagenation.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationUtilsTest {

    private List<String> data;

    @BeforeEach
    void setUp() {
        data = List.of("A" , "B", "C", "D", "E");
    }

    @Test
    @DisplayName("정상적인 페이지를 반환한다.")
    void PageResponse ()  {
        //given
        int page = 1;
        int size = 2;

        //when
        PageResponse<String> result = PaginationUtils.toPageResponse(data, page, size);
        //then
        assertThat(result.content()).containsExactly("A", "B");

            }

        @Test
        @DisplayName("페이지 범위 초과시 빈리스트를 반환")
        void EmptyListWhenPageIsOutOfBounds() {
        // Given
            int page = 10; // out of bounds
            int size = 2;

            //when
            PageResponse<String> result = PaginationUtils.toPageResponse(data, page, size);

            //then
            assertThat(result.content()).isEmpty();
        }

        @Test
        @DisplayName("페이지 크기가 전체 데이터 크기보다 큰 경우 전체 데이터를 반환한다.")
        void ReturnAllElements() {
            // Given
            int page = 1;
            int size = 10;

            // When
            PageResponse<String> result = PaginationUtils.toPageResponse(data, page, size);

            // Then
            assertThat(result.content()).containsExactly("A", "B" , "C" , "D" , "E");
        }

    }

