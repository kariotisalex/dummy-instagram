package com.itsaur.internship.comment.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
public record CommentQueryModel(
        List<CommentsQueryModel> commentQueryModels,
        Long count) {
    public record CommentsQueryModel(

            UUID commentid,
            OffsetDateTime createdate,
            String comment,
            UUID userid,
            String username,
            UUID postid
    ) {
    }
}