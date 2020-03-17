package com.repostit.repostit.Service;

import com.repostit.repostit.Dao.Community;
import com.repostit.repostit.Dto.CommunityDto;
import com.repostit.repostit.Dto.PostResponse;
import com.repostit.repostit.Exceptions.RepostitException;
import com.repostit.repostit.Mapper.CommunityMapper;
import com.repostit.repostit.Repository.CommunityRepository;
import com.repostit.repostit.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.repostit.repostit.Utilities.Constants.COMMUNITY_NOT_FOUND_WITH_ID;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommunityMapper communityMapper;

    @Transactional(readOnly = true)
    public List<CommunityDto> getAll() {
        return communityRepository.findAll()
                .stream()
                .map(communityMapper::mapCommunityToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RepostitException(COMMUNITY_NOT_FOUND_WITH_ID + id));
        return postRepository.findAllByCommunity(community)
                .stream()
                .map(postService::mapToDto)
                .collect(toList());
    }

    @Transactional
    public CommunityDto save(CommunityDto communityDto) {
        Community community = communityRepository.save(communityMapper.mapDtoToCommunity(communityDto));
        communityDto.setId(community.getId());
        return communityDto;
    }

    @Transactional(readOnly = true)
    public CommunityDto getCommunity(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RepostitException(COMMUNITY_NOT_FOUND_WITH_ID + id));
        return communityMapper.mapCommunityToDto(community);
    }
}
