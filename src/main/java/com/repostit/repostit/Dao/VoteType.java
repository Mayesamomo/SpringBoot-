package com.repostit.repostit.Dao;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    VoteType(int direction) {
    }
}
