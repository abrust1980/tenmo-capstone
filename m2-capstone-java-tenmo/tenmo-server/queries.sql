--select account
SELECT account.user_id, username, account_id, tenmo_user.user_id, balance
FROM tenmo_user
JOIN account ON account.user_id = tenmo_user.user_id
WHERE username = ?