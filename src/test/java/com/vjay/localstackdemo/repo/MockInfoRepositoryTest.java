package com.vjay.localstackdemo.repo;

import com.vjay.localstackdemo.model.MockInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE)
public class MockInfoRepositoryTest {

    private MockInfo mockInfoOne, mockInfoTwo, mockInfoThree;
    @Autowired
    private MockInfoRepository mockInfoRepository;

    @BeforeEach
    void setUp() {
        mockInfoOne = new MockInfo(1L, "key1", "val1");
        mockInfoTwo = new MockInfo(2L, "key2", "val2");
        mockInfoThree = new MockInfo(3L, "key3", "val3");
        mockInfoRepository.saveAll(List.of(mockInfoOne, mockInfoTwo, mockInfoThree));
    }

    @Test
    void shouldReturnAllMockInfos() {

        Optional<MockInfo> mockInfoOptional = mockInfoRepository.findById(2L);
        assertTrue(mockInfoOptional.isPresent());
        assertThat(mockInfoOptional.get()).usingRecursiveComparison()
                .isEqualTo(mockInfoTwo);
    }
}
