USE movies;
/*перерывы 30 минут и более между фильмами — выводить по уменьшению длительности перерыва. 
Колонки «фильм 1», «время начала», «длительность», «время начала второго фильма», «длительность перерыва»*/


select 
 movies.name 'фильм 1',
 list.show_time 'время начала',
movies.duration 'длительность',
(select l1.show_time from `list` l1 where l1.list_id=list.list_id+1) 'время начала второго фильма',
 TIMEDIFF((select l1.show_time from `list` l1 where l1.list_id=list.list_id+1), date_add(show_time, INTERVAL duration MINUTE)) AS 'длительность перерыва'
from `list` 
inner join `movies` ON list.movies_id=movies.movies_id
inner join show_time ON list.show_time_id=show_time.show_time_id 
where TIMEDIFF((select l1.show_time from `list` l1 where l1.list_id=list.list_id+1), date_add(show_time, INTERVAL duration MINUTE)) >= '00:30'
order by 'длительность перерыва' desc