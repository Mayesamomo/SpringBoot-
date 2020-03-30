package com.repostit.repostit.Service;

import com.repostit.repostit.Dao.Community;
import com.repostit.repostit.Dto.CommunityDto;
import com.repostit.repostit.Exceptions.RepostitException;
import com.repostit.repostit.Mapper.CommunityMapper;
import com.repostit.repostit.Repository.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    @Transactional
    public CommunityDto save(CommunityDto communityDto) {
        Community save = communityRepository.save(communityMapper.mapDtoToCommunity(communityDto));
        communityDto.setId(save.getId());
        return communityDto;
    }

    @Transactional(readOnly = true)
    public List<CommunityDto > getAll() {
        return communityRepository.findAll()
                .stream()
                .map(communityMapper::mapCommunityToDto)
                .collect(toList());
    }

    public CommunityDto  getCommunity(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RepostitException("No community found with ID - " + id));
        return communityMapper.mapCommunityToDto(community);
    }
}
