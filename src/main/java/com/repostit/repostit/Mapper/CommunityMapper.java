package com.repostit.repostit.Mapper;

import com.repostit.repostit.Dao.Community;
import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dto.CommunityDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "postCount", expression = "java(mapPosts(community.getPosts()))")
    CommunityDto mapCommunityToDto(Community community);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Community mapDtoToCommunity(CommunityDto community);
}
