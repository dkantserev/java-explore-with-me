

INSERT INTO public.users (id, email, name)
VALUES (DEFAULT, 'test@mai.ru', 'tester');

INSERT INTO public.users (id, email, name)
VALUES (DEFAULT, 'user@mail.ru', 'user');

INSERT INTO public.category (id, name)
VALUES (1, 'cinema');

INSERT INTO public.category (id, name)
VALUES (2, 'dancing');

INSERT INTO public.category (id, name)
VALUES (3, 'work');

INSERT INTO public.locations (id, lat, lon)
VALUES (DEFAULT, 59.83059, 30.37745);

INSERT INTO public.event (id, annotation, available, category, description, event_date, paid, participant_limit,
                          request_moderation, state, title, views, location_id, user_id)
VALUES (DEFAULT, 'cinema', true, 1, 'кинотеатр', '2022-10-31 19:29:53.000000', true, 50, true, 0, 'сштуьф', 0, 1, 1);



