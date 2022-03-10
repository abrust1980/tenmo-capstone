--select account
SELECT account.user_id, username, account_id, tenmo_user.user_id, balance
FROM tenmo_user
JOIN account ON account.user_id = tenmo_user.user_id
WHERE account.user_id = 1001

--select all accounts not yours
SELECT account.user_id, username, account_id, tenmo_user.user_id, balance
FROM tenmo_user
JOIN account ON account.user_id = tenmo_user.user_id
WHERE username != ?;

--create a new transfer
INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)
VALUES(
	(SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc ILIKE ?),
	(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc ILIKE ?),
	(SELECT account_id FROM account WHERE user_id = ?),
	(SELECT account_id FROM account WHERE user_id = ?),
	?
)

SELECT account_id FROM account WHERE user_id = 1001





SELECT * FROM transfer

SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Request'
SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Approved'

SELECT account_id FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE username = 'user1'
