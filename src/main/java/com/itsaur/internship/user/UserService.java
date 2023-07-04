package com.itsaur.internship.user;
import com.itsaur.internship.comment.CommentStore;
import com.itsaur.internship.post.PostService;
import com.itsaur.internship.post.PostStore;
import io.vertx.core.Future;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserService {

    private PostStore postStore;
    private UsersStore usersStore;
    private CommentStore commentStore;
    public UserService( PostStore postStore, UsersStore usersStore, CommentStore commentStore) {
        this.postStore = postStore;
        this.usersStore = usersStore;
        this.commentStore = commentStore;
    }


    public Future<Void> register(String username, String password) {
         return usersStore.findUserByUsername(username)
                 .otherwiseEmpty()
                 .compose(q -> {
                    if (q == null)
                        return usersStore.insert(new User(UUID.randomUUID(), LocalDateTime.now(), username, password));
                    else
                        return Future.failedFuture(new IllegalArgumentException("User exists!"));
                 });
    }


    public Future<User> login(String username, String password){
        return usersStore.findUserByUsername(username)
                .onFailure(e -> {
                    System.out.println(e);
                })
                .compose(user -> {
                    if (user.isPasswordEqual(password)){
                        return Future.succeededFuture(user);
                    } else {
                        return Future.failedFuture(new IllegalArgumentException("Invalid Password"));
                    }
                });
    }

    public Future<Void> deleteByUserid(String userid){
        UUID useridUUID = UUID.fromString(userid);
        return usersStore.findUserByUserid(useridUUID)
                .onFailure(e ->{
                    System.out.println(e);
                })
                .compose(user -> {
                    if(user.isUseridEqual(useridUUID)){
                        return new PostService(postStore,usersStore,commentStore).deleteAllPosts(useridUUID)
                                .compose(q -> {
                                    return usersStore.delete(useridUUID);
                                });
                    } else {
                        return Future.failedFuture(new IllegalArgumentException("User doesn't exist."));
                    }
                });
    }


    public Future<Void> changePassword(UUID userid, String currentPassword, String newPassword){
        return usersStore.findUserByUserid(userid)
                .onFailure(e -> {
                    e.printStackTrace();
                })
                .compose(user -> {
                    if (user.isPasswordEqual(currentPassword)){
                        user.setUpdatedate(LocalDateTime.now());
                        user.setPassword(newPassword);

                        return usersStore.update(user);
                    }else {
                        return Future.failedFuture(new IllegalArgumentException("The password is wrong."));
                    }
                });
    }
}