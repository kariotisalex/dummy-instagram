package com.itsaur.internship;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.itsaur.internship.comment.CommentService;
import com.itsaur.internship.comment.CommentStore;
import com.itsaur.internship.comment.PostgresCommentStore;
import com.itsaur.internship.post.PostService;
import com.itsaur.internship.post.PostStore;
import com.itsaur.internship.post.PostgresPostStore;
import com.itsaur.internship.user.PostgresUsersStore;
import com.itsaur.internship.user.UserService;
import com.itsaur.internship.user.UsersStore;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.PoolOptions;

public class Application {


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        PostgresOptions postgresOptions = new PostgresOptions();
        final PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        try{
            JCommander.newBuilder()
                    .addObject(postgresOptions)
                    .build()
                    .parse(args);
        }catch (ParameterException e){
            System.out.println(e.getMessage());
        }
        PostgresPostStore postgresPostStore = new PostgresPostStore(vertx, postgresOptions.getPgConnectOptions(), poolOptions);
        PostgresUsersStore postgresUsersStore = new PostgresUsersStore(vertx, postgresOptions.getPgConnectOptions(),poolOptions);
        PostgresCommentStore postgresCommentStore = new PostgresCommentStore(vertx, postgresOptions.getPgConnectOptions(), poolOptions);



        final UserService service = new UserService(
                postgresPostStore,
                postgresUsersStore,
                postgresCommentStore
        );

        if (postgresOptions.getService().equals("serverdb")){
            PostStore postStore = new PostgresPostStore(vertx,postgresOptions.getPgConnectOptions(),poolOptions);
            CommentStore commentStore = new PostgresCommentStore(vertx, postgresOptions.getPgConnectOptions(),poolOptions);
            UsersStore userStore = new PostgresUsersStore(vertx, postgresOptions.getPgConnectOptions(),poolOptions);

            vertx.deployVerticle(
                    new VerticleApi(
                        new UserService(postStore,userStore,commentStore),
                        new CommentService(commentStore),
                        new PostService(postStore, userStore, commentStore)
                    )
            );

        }else if (postgresOptions.getService().equals("console")) {
            new UserConsole(service).executeCommand(args)
                    .onComplete(v -> System.exit(0));
        } else {
            System.out.println("Something went wrong!");
        }




    }
}
