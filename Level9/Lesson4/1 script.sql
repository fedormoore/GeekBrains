USE movies;
/*ошибки в расписании (фильмы накладываются друг на друга), отсортированные по возрастанию времени. 
Выводить надо колонки «фильм 1», «время начала», «длительность», «фильм 2», «время начала», «длительность»;*/


select
 movies.name,
 list.show_time 'start time 2',
movies.duration,
(select t3.name
                 from list t2 inner join movies t3 ON t2.movies_id=t3.movies_id
                where 
                t2.list_id <> list.list_id
                and t2.show_date=list.show_date
                and list.show_time <=date_add(t2.show_time, INTERVAL t3.duration MINUTE) and  list.show_time >=t2.show_time) 'name 2',
 (select t2.show_time
                 from list t2 inner join movies t3 ON t2.movies_id=t3.movies_id
                where 
                t2.list_id <> list.list_id
                and t2.show_date=list.show_date
                and list.show_time <=date_add(t2.show_time, INTERVAL t3.duration MINUTE) and  list.show_time >=t2.show_time) 'start time 2',
  (select t3.duration
                 from list t2 inner join movies t3 ON t2.movies_id=t3.movies_id
                where 
                t2.list_id <> list.list_id
                and t2.show_date=list.show_date
                and list.show_time <=date_add(t2.show_time, INTERVAL t3.duration MINUTE) and  list.show_time >=t2.show_time)   'duration 2'            
from `list` 
inner join `movies` ON list.movies_id=movies.movies_id
inner join show_time ON list.show_time_id=show_time.show_time_id 
where 
               exists (select * 
                 from list t2 inner join movies t3 ON t2.movies_id=t3.movies_id
                where 
                t2.list_id <> list.list_id
                and t2.show_date=list.show_date
                and list.show_time <=date_add(t2.show_time, INTERVAL t3.duration MINUTE) and  list.show_time >=t2.show_time                 
               )
