package com.itsaur.internship.comment;

import com.itsaur.internship.post.PostStore;
import com.itsaur.internship.user.UsersStore;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.List;
import java.util.UUID;

public class CommentService {
    private CommentStore commentStore;
    private PostStore postStore;
    private UsersStore usersStore;
    Vertx vertx;

    public CommentService(Vertx vertx, CommentStore commentStore) {
        this.vertx = vertx;
        this.commentStore = commentStore;
    }

   public Future<Void> addComment(String comment, String username, String filename){
        return Future.all(this.usersStore.findUserByUsername(username),
                   this.postStore.findPostByFilename(filename))
                .compose(res -> {
                    return this.commentStore.insert(new Comment(comment,
                                                                res.resultAt(0),
                                                                res.resultAt(1)));
                });
   }

   public Future<Void> changeComment(UUID commentid, String comment){
        return this.commentStore.update(new Comment(commentid, comment));
   }

   public Future<Void> deleteComment(UUID commentid){
        return this.commentStore.deleteById(commentid);
   }


   public Future<List<Comment>> retrieveAll(UUID commentid){
        return this.commentStore.readAllById(commentid);
    }
}
