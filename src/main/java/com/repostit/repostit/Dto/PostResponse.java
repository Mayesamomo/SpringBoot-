package com.repostit.repostit.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private Integer votesNum;
    private String username;
    private boolean upVote;
    private boolean downVote;
    private String communityName;
    private Integer commentNum;
    private String duration;
}
