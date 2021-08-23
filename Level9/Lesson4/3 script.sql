USE movies;
/*список фильмов, для каждого — с указанием общего числа посетителей за все время, 
среднего числа зрителей за сеанс и общей суммы сборов по каждому фильму (отсортировать по убыванию прибыли). 
Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу*/


(select
 list.list_id,
 movies.name 'Фильмы',
(select count(t1.list_id) from tickets t1 inner join list l1 ON t1.list_id = l1.list_id 
inner join show_time sh1 ON l1.show_time_id=sh1.show_time_id where l1.movies_id = list.movies_id) 'Общее',
(select count(t1.list_id) from tickets t1 inner join list l1 ON t1.list_id = l1.list_id 
inner join show_time sh1 ON l1.show_time_id=sh1.show_time_id where l1.movies_id = list.movies_id)/
(select count(l1.list_id) from tickets t1 inner join list l1 ON t1.list_id = l1.list_id where l1.list_id = list.list_id and l1.movies_id=list.movies_id) 'Среднее кол',


(select sum(sh1.price) from tickets t1 inner join list l1 ON t1.list_id = l1.list_id 
inner join show_time sh1 ON l1.show_time_id=sh1.show_time_id where l1.movies_id = list.movies_id) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id
group by movies.name

)

UNION ALL
select distinct
 null,
'Итого:',
(select count(t1.list_id) from tickets t1 ),

(select count(t1.list_id) from tickets t1 )/
(select count(l1.list_id) from list l1 ) 'Среднее кол',


(select sum(sh1.price) from tickets t1 inner join list l1 ON t1.list_id = l1.list_id 
inner join show_time sh1 ON l1.show_time_id=sh1.show_time_id )

from `list` 
inner join `movies` ON list.movies_id=movies.movies_id
inner join show_time ON list.show_time_id=show_time.show_time_id 
inner join tickets ON list.list_id = tickets.list_id

order by 5 asc