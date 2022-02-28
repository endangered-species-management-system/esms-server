create table case_researcher
(
    case_id       UUID not null,
    researcher_id UUID not null,
    primary key (case_id, researcher_id)
);
create table evidence
(
    evidence_id          UUID          not null,
    archived             boolean       not null,
    content_type         varchar(255)  not null,
    created              timestamp     not null,
    external_key         UUID          not null,
    resource_key         varchar(255)  not null,
    image_name           varchar(255)  not null,
    not_image            boolean       not null,
    note                 NVARCHAR(MAX) not null,
    evidence_number      varchar(255)  not null,
    updated              timestamp     not null,
    species_case_case_id UUID          not null,
    storage_storage_id   UUID          not null,
    primary key (evidence_id)
);
create table person
(
    person_id    UUID         not null,
    external_key UUID         not null,
    first_name   varchar(255) not null,
    hire_date    timestamp    not null,
    last_name    varchar(255) not null,
    oauth_key    varchar(255) not null,
    updated      timestamp    not null,
    user_name    varchar(255) not null,
    primary key (person_id)
);
create table researcher
(
    researcher_id UUID         not null,
    card_id       varchar(255) not null,
    created       timestamp    not null,
    external_key  UUID         not null,
    status        varchar(255),
    title         varchar(255),
    updated       timestamp    not null,
    person_id     UUID,
    primary key (researcher_id)
);
create table species_case
(
    case_id              UUID         not null,
    created              timestamp    not null,
    detailed_description varchar(255),
    external_key         UUID         not null,
    case_number          varchar(255) not null,
    phase                varchar(255),
    summary              varchar(255),
    updated              timestamp    not null,
    lead_researcher      UUID         not null,
    primary key (case_id)
);
create table storage
(
    storage_id   UUID         not null,
    created      timestamp    not null,
    external_key UUID         not null,
    location     varchar(255) not null,
    storage_name varchar(255) not null,
    updated      timestamp    not null,
    primary key (storage_id)
);
create table track
(
    track_id         UUID      not null,
    created          timestamp not null,
    external_key     UUID      not null,
    purpose          varchar(255),
    track_type       varchar(255),
    track_evidence   UUID,
    track_researcher UUID      not null,
    primary key (track_id)
);
alter table evidence
    add constraint UK_nwpn67vcy9cata9kda7da3der unique (external_key);
alter table evidence
    add constraint UK_ftqhvul2ydx60qu5ck9js4lyf unique (note);
alter table evidence
    add constraint UK_ia9t7wcrxp4m65w9m0tfpw2qk unique (evidence_number);
create index IDXr2q6277xgupbd6jh7upywc026 on person (hire_date);
create index IDXdllm8el7vyb74m7po97norfah on person (user_name);
alter table person
    add constraint UK_pikwtqn1wplu5o4j6lxfh5jvp unique (external_key);
alter table person
    add constraint UK_412nyh4cp80cscaorp5rlbh0i unique (oauth_key);
alter table person
    add constraint UK_e76i3q6dk7y68q7vpkobc71tx unique (user_name);
create index IDX974ynpm22ayx8f8bxe3xylmr0 on researcher (person_id, card_id, created);
alter table researcher
    add constraint UK_175jp98ohs637mxbg0yfu1577 unique (card_id);
alter table researcher
    add constraint UK_eq4q0a65d4ipjoepaba4p85xf unique (external_key);
create index IDX8uviqwsl7l6nnmdjh979hlgn on species_case (case_number, created);
alter table species_case
    add constraint UK_90itki1fv86vg8nfoq9vkv754 unique (external_key);
alter table species_case
    add constraint UK_b9gm0vsjwavasndaibh2vhwyi unique (case_number);
alter table storage
    add constraint UK_32t66nn77i54m8suw7xtl433p unique (external_key);
alter table track
    add constraint UK_mm3n5apulumbltsev870c4ahb unique (external_key);
alter table case_researcher
    add constraint FK1x771k6p1l531bffte3llu8sy foreign key (researcher_id) references researcher;
alter table case_researcher
    add constraint FK991bnap9mu6vny0amlis52j8 foreign key (case_id) references species_case;
alter table evidence
    add constraint FKkjy6ulbyg4nn4lquuhcx5ppsr foreign key (species_case_case_id) references species_case;
alter table evidence
    add constraint FK4qt2dmi8xn3wsgiv0g0vraf8u foreign key (storage_storage_id) references storage;
alter table researcher
    add constraint FK7j3dcxeohmkbb89hcm5jr3amh foreign key (person_id) references person;
alter table species_case
    add constraint FK5teiisfemvyya2w6ibmigae5k foreign key (lead_researcher) references researcher;
alter table track
    add constraint FK20idlxdgcsd6ry0jyrgwwc1dj foreign key (track_evidence) references evidence;
alter table track
    add constraint FK9qy2mk1535nb8v27ty25u8m4l foreign key (track_researcher) references researcher;
