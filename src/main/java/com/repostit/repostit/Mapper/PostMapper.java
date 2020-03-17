package com.repostit.repostit.Mapper;

import com.repostit.repostit.Dao.Community;
import com.repostit.repostit.Dao.Post;
import com.repostit.repostit.Dao.User;
import com.repostit.repostit.Dto.PostRequest;
import com.repostit.repostit.Dto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Community community, User user);
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "communityName", source = "community.name")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapToDto(Post post);
}
