package com.repostit.repostit.Controller;

import com.repostit.repostit.Dto.CommunityDto;
import com.repostit.repostit.Service.CommunityService;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping
    public List<CommunityDto> getAllCommunity() {
        return communityService.getAll();
    }

    @GetMapping("/{id}")
    public CommunityDto getCommunity(@PathVariable Long id) {
        return communityService.getCommunity(id);
    }

    @PostMapping
    public CommunityDto create(@RequestBody @Valid CommunityDto communityDto) {
        return communityService.save(communityDto);
    }
}
