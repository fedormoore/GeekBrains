USE movies;
/*число посетителей и кассовые сборы, сгруппированные по времени начала фильма: 
с 9 до 15, с 15 до 18, с 18 до 21, с 21 до 00:00 (сколько посетителей пришло с 9 до 15 часов и т.д.)
*/

(select  
'  9 - 15' name,
count(tickets.list_id) 'Общее',
sum(show_time.price) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id
where list.show_time between '9:00:00' and '14:59:59'
)

UNION ALL

(select  
'15 - 18' name,
count(tickets.list_id) 'Общее',
sum(show_time.price) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id
where list.show_time between '15:00:00' and '17:59:59'
)

UNION ALL

(select  
'18 - 21' name,
count(tickets.list_id) 'Общее',
sum(show_time.price) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id
where list.show_time between '18:00:00' and '21:59:59'
)

UNION ALL

(select  
'21 - 00' name,
count(tickets.list_id) 'Общее',
sum(show_time.price) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id
where list.show_time between '21:00:00' and '23:59:59'
)
UNION ALL

(select  
'Итого:' name,
count(tickets.list_id) 'Общее',
sum(show_time.price) 'Сумма сбора'

from `list` 
left join `movies` ON list.movies_id=movies.movies_id
left join show_time ON list.show_time_id=show_time.show_time_id 
left join tickets ON list.list_id = tickets.list_id

)