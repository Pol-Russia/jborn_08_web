4.a
select p.full_name as name, account.id, account.balance  from person as p, account
where (p.id = account.person_id)


select p.full_name as name, account.id, account.balance  from person as p inner join account
on p.id = account.person_id
order by p.full_name;

4.b
select p.full_name as name, a.id as account, t.id, t.sum, t.date  from transaction as t
inner join account a on (t.account_id = a.id and t.date = (now()::date - '1 day'::interval)::date)
inner join person p on a.user_list_id = p.id
order by p.full_name;

--(now()::date - '1 day'::interval)::date) Получение вчерашней даты в postgres

4.c
select  p.full_name as name, sum(account.balance) from person as p, account
where p.id = account.person_id
group by p.full_name;

select  p.full_name as name, sum(account.balance) from person as p inner join account
on p.id = account.person_id
group by p.id;