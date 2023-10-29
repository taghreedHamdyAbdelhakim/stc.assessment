


SELECT user_id, training_id, user_name ,training_date ,COUNT(*) as num_training
FROM Training_details  join users on (id=user_id)
GROUP BY user_id, user_name ,training_id , training_date
HAVING COUNT(*) > 1
ORDER BY MAX(training_date) DESC;