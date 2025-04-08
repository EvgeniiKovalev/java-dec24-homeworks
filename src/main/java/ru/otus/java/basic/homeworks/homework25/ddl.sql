/*
Домашнее задание
Проектирование схемы базы данных

Цель:
получить базовые навыки в разработке схемы базы данных.


Описание/Пошаговая инструкция выполнения домашнего задания:
Разработать структуру БД для хранения теста, Тест состоит из вопросов, для каждого из них есть от 2 до 5 вариантов ответа, один из которых правильный
Установить PostgreSQL и одну из систем работы с БД
Проверить работоспособность структуры
** (Опционально) Разработать структуру БД для хранения многих тестов, описанных в предыдущем пункте.

Результат - Набор DDL скриптов, создающих БД
*/

--очистка
drop table if exists db_tests.answers;
drop table if exists db_tests.questions;
drop table if exists db_tests.tests;
drop schema if exists db_tests;


--------------
-- 1. ТАБЛИЦЫ
--------------

--create database tests:

create schema if not exists db_tests;

create table if not exists  db_tests.tests (
    test_id serial not null primary key,
    test_text varchar(255) not null
);
comment on table db_tests.tests is 'таблица для хранения тестов';
comment on column db_tests.tests.test_id is 'уникальный идентификатор теста';
comment on column db_tests.tests.test_text is 'название теста';

create table if not exists  db_tests.questions (
    question_id serial not null primary key,
    test_id integer not null references tests.db_tests.tests(test_id)
        on delete cascade
        on update cascade,
    question_text varchar(4000) not null
);
comment on table db_tests.questions is 'таблица для хранения вопросов тестов';
comment on column db_tests.questions.question_id is 'уникальный идентификатор вопроса теста';
comment on column db_tests.questions.test_id is 'уникальный идентификатор теста';
comment on column db_tests.questions.question_text is 'вопрос теста';


create table if not exists db_tests.answers (
    answer_id serial not null primary key,
    question_id integer not null references tests.db_tests.questions(question_id)
        on delete cascade
        on update cascade,
    answer_text varchar(4000) not null,
    answer_right BOOLEAN null
);
comment on table db_tests.answers is 'таблица для хранения ответов теста';
comment on column db_tests.answers.answer_id is 'уникальный идентификатор ответа теста';
comment on column db_tests.answers.question_id is 'уникальный идентификатор вопроса теста';
comment on column db_tests.answers.answer_text is 'текст ответа';
comment on column db_tests.answers.answer_right is 'true если правильный ответ, иначе null';

--индекс на FK
CREATE INDEX if not exists idx_fk_answers_question_id ON db_tests.answers(question_id);

--------------
--2. триггеры
--------------
drop trigger if exists trigger_truncate_answer_count ON db_tests.answers;
drop function if exists before_truncate_answers();
CREATE OR REPLACE FUNCTION before_truncate_answers()
RETURNS TRIGGER AS $$
DECLARE
v_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM db_tests.questions;
    IF v_count > 0 THEN
        RAISE EXCEPTION 'Операция TRUNCATE запрещена для таблицы db_tests.answers';
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_truncate_answer_count
BEFORE TRUNCATE ON db_tests.answers
FOR EACH STATEMENT
EXECUTE FUNCTION before_truncate_answers();


drop trigger if exists trigger_check_answer_insert on db_tests.answers;
DROP FUNCTION IF EXISTS check_answer_insert();
CREATE OR REPLACE FUNCTION check_answer_insert()
RETURNS TRIGGER AS $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM db_tests.answers
    WHERE question_id = NEW.question_id;

    IF (v_count >= 5)
    THEN
        RAISE EXCEPTION 'Количество ответов должно быть от 2 до 5';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_check_answer_insert
BEFORE INSERT ON db_tests.answers
FOR EACH ROW
EXECUTE FUNCTION check_answer_insert();


drop trigger if exists trigger_check_answer_delete on db_tests.answers;
DROP FUNCTION IF EXISTS check_answer_delete();
CREATE OR REPLACE FUNCTION check_answer_delete()
RETURNS TRIGGER AS $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM db_tests.answers
    WHERE question_id = OLD.question_id;

    IF (v_count <= 2)
    THEN
        RAISE EXCEPTION 'Количество ответов должно быть от 2 до 5';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_answer_delete
BEFORE DELETE ON db_tests.answers
FOR EACH ROW
EXECUTE FUNCTION check_answer_delete();


drop trigger if exists trigger_check_answer_update on db_tests.answers;
DROP FUNCTION IF EXISTS check_answer_update();
CREATE OR REPLACE FUNCTION check_answer_update()
RETURNS TRIGGER AS $$
DECLARE
    v_count_old INTEGER;
    v_count_new INTEGER;
BEGIN
    IF (NEW.QUESTION_ID <> OLD.QUESTION_ID) THEN
        select
            sum(case when question_id = NEW.QUESTION_ID then 1 else 0 end)+1,
            sum(case when question_id = OLD.QUESTION_ID then 1 else 0 end)-1
        into v_count_new, v_count_old
        from db_tests.answers
        where question_id in (NEW.QUESTION_ID, OLD.QUESTION_ID);

        IF NOT((V_COUNT_OLD BETWEEN 2 AND 5) AND (V_COUNT_NEW BETWEEN 2 AND 5))
        THEN
            RAISE EXCEPTION 'Количество ответов должно быть от 2 до 5';
        END IF;

    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_answer_update
BEFORE UPDATE ON db_tests.answers
FOR EACH ROW
EXECUTE FUNCTION check_answer_update();


------------------
--3. тесты триггеров
------------------
--3.1 тестовые данные
insert into db_tests.tests(test_id, test_text) values(1, 'test 1');

INSERT INTO db_tests.questions(test_id, question_id, question_text) values(1, 1,'question_1');
INSERT INTO db_tests.questions(test_id, question_id, question_text) values(1, 2, 'question_2');

INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(1, 1, 'answer_1_1', true);
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(2, 1, 'answer_1_2', null);

INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(3, 2, 'answer_2_1', true);
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(4, 2, 'answer_2_2', null);
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(5, 2, 'answer_2_3', null);
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(6, 2, 'answer_2_4', null);
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(7, 2, 'answer_2_5', null);



--3.2 проверка триггера truncate
truncate table db_tests.answers -- error

-- проверка триггера delete
delete from db_tests.answers where question_id = 1 and answer_id = 1; --error

-- проверка триггера insert
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(8, 2, 'answer_2_6', null); --error
INSERT INTO db_tests.answers(answer_id, question_id, answer_text, answer_right) values(8, 1, 'answer_1_3', null); --no error

-- 3.3 проверка триггера update
update db_tests.answers set answer_right = null where answer_id = 6; --no errors
update db_tests.answers set question_id = 2 where answer_id in (5,6); -- no error
update db_tests.answers set question_id = 2 where answer_id = 8; -- error
