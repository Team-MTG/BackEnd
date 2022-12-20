package com.mtg.Motugame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
class MotugameApplicationTests {

    @MockBean
    private  matchingDetailService;

    @Autowired
    private MockMvc mockMvc;

	@Test
    public void findUser_success() throws Exception{
        DetailMatchingPostResponseDto matchingRoom = DetailMatchingPostResponseDto.builder()
                .gender(2)
                .ownerUser("유해찬")
                .peopleCount(3)
                .peopleMax(4)
                .promiseLocation("광운대학교 윤스쿡")
                .promiseTime(LocalDateTime.now())
                .restaurantName("윤스쿡")
                .shortText("그건바로나~")
                .build();

        given(matchingDetailService.findByRoomId(any())).willReturn(matchingRoom);

        mockMvc.perform(
                        get("/api/detail-matching-post").param("roomId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("promiseTime").exists())
                .andDo(print());

        verify(matchingDetailService).findByRoomId(1L);
    }

    @Test
    public void findUser_fail() throws Exception{

    }



}
