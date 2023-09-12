CREATE TABLE users
(
    userid uuid primary key not null,
    createdate timestamptz,
    updatedate timestamptz,
    username varchar,
    password varchar
);

CREATE TABLE posts (
    postid uuid primary key not null,
    createdate timestamptz,
    updatedate timestamptz,
    filename varchar,
    description varchar,
    userid uuid references users(userid)
);

CREATE TABLE comments
(
    commentid uuid primary key not null,
    createdate timestamptz,
    updatedate timestamptz,
    comment varchar,
    userid uuid references users(userid),
    postid uuid references posts(postid)
);