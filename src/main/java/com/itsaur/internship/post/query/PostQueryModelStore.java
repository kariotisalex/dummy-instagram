package com.itsaur.internship.post.query;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

import java.util.List;
import java.util.UUID;

public interface PostQueryModelStore {
    Future<PostQueryModel> findById(UUID postId);



    public Future<List<PostQueryModel>> findPostPageByUid(UUID uid);

    public Future<String> countAllPostsbyUid(UUID uid);
    public Future<List<PostQueryModel>> findPostPageByUid(UUID uid, int startFrom, int size);
}
