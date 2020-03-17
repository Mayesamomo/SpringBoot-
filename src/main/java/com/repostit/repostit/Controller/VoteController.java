package com.repostit.repostit.Controller;

import com.repostit.repostit.Dto.PostResponse;
import com.repostit.repostit.Dto.VoteDto;
import com.repostit.repostit.Service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public PostResponse vote(@Valid @RequestBody VoteDto voteDto) {
        return voteService.vote(voteDto);
    }
}
