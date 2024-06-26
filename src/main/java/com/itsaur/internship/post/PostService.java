package com.itsaur.internship.post;


import com.itsaur.internship.comment.CommentStore;
import com.itsaur.internship.user.User;
import com.itsaur.internship.user.UsersStore;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostService {

    private Vertx vertx;
    private PostStore postStore;
    private UsersStore usersStore;
    private CommentStore commentStore;

    public PostService(Vertx vertx, PostStore postStore, UsersStore usersStore, CommentStore commentStore) {
        this.vertx = vertx;
        this.postStore = postStore;
        this.usersStore = usersStore;
        this.commentStore = commentStore;
    }

    public Future<Void> addPost(UUID userid, String filename, String description) {
        return this.usersStore.findUserByUserid(userid)
                .onFailure(e -> {
                    e.printStackTrace();
                })
                .compose(user -> {
                    ;
                    return this.postStore.insert(
                            new Post(
                                    UUID.randomUUID(),
                                    OffsetDateTime.now(),
                                    null,
                                    filename,
                                    description,
                                    user.userid()));
                });
    }

    public Future<Void> updatePost(UUID userid, UUID postid, String description){
        return Future.all(this.usersStore.findUserByUserid(userid),this.postStore.findPostByPostid(postid))
                .compose(res -> {
                    User user = res.resultAt(0);
                    Post post = res.resultAt(1);
                    if (post.userid().equals(user.userid())){

                        return this.postStore.updatePost(new Post(
                                post.postid(),
                                post.createdDate(),
                                OffsetDateTime.now(),
                                post.filename(),
                                description,
                                post.userid()

                        ));
                    }else{
                        System.out.println("There is no match between user and post!");
                        return Future.failedFuture(new IllegalArgumentException("There is no match between user and post!"));
                    }

                });
    }

    public Future<Void> deleteAllPosts(UUID userid){
        return this.postStore.readAllByUserid(userid)
                .otherwiseEmpty()
                .compose(res -> {
                    if (res == null) {
                        System.out.println("There is no posts in this userid");
                        return Future.succeededFuture();
                    } else {
                        List<Future<Void>> futureList = res
                                .stream()
                                .map(w -> {
                                    System.out.println("asd "+w.filename());
                                    return deletePost(w.userid(),w.postid());
                                })
                                .collect(Collectors.toList());
                    //System.out.println(futureList);
                        return Future.all(futureList)
                                .onFailure(e -> {
                                    e.printStackTrace();
                                }).compose(q -> {
                                    System.out.println(q);
                                    return Future.succeededFuture();
                                });
                    }
                });
    }



    public Future<Void> deletePost(UUID userid, UUID postid){
        return this.usersStore.findUserByUserid(userid)
                .compose(res ->{
                    return this.commentStore.deleteByPostid(postid)
                            .compose(q -> {
                                return this.postStore.findPostByPostid(postid)
                                        .compose(w -> {
                                            return this.postStore.delete(postid)
                                                    .compose(re -> {
                                                        return vertx
                                                                .fileSystem()
                                                                .delete(String.valueOf(Paths.get("images", w.filename()).toAbsolutePath()))
                                                                .onFailure(e -> {
                                                                    e.printStackTrace();
                                                                });
                                            });
                                        });
                            });
                });

    }





    public Future<List<Post>> retrieveAll(UUID userid){
        return this.postStore.readAllByUserid(userid)
                .onFailure(e -> {
                    e.printStackTrace();
                })
                .compose(c -> {
                    return this.postStore.readAllByUserid(userid);
                });
    }

}
