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

--get transfers with desc
SELECT transfer_id, transfer_type_desc, transfer_status_desc, amount, user_to.username AS username_to, user_from.username AS username_from FROM transfer
JOIN account acc_from ON acc_from.account_id = transfer.account_from
JOIN account acc_to ON acc_to.account_id = transfer.account_to
JOIN tenmo_user user_from ON acc_from.user_id = user_from.user_id
JOIN tenmo_user user_to ON acc_to.user_id = user_to.user_id
JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id
JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id





SELECT account_id FROM account WHERE user_id = 1001

UPDATE account SET balance = balance + 10000 WHERE user_id = 1001
UPDATE account SET balance = balance - ? WHERE user_id = ?

select * from account



SELECT * FROM transfer

SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Request'
SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Approved'

SELECT account_id FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE username = 'user1'


SELECT * FROM transfer
JOIN account ON account.account_id = transfer.account_to
JOIN tenmo_user ON account.user_id = tenmo_user.user_id
WHERE account_from = (SELECT account_id FROM account WHERE user_id = 1001)
		OR account_to =(SELECT account_id FROM account WHERE user_id = 1001)


SELECT * FROM transfer
JOIN account ON account.account_id = transfer.account_to
JOIN tenmo_user ON account.user_id = tenmo_user.user_id
WHERE username = 'user1' 
UNION
SELECT * FROM transfer
JOIN account ON account.account_id = transfer.account_from
JOIN tenmo_user ON account.user_id = tenmo_user.user_id
WHERE username = 'user1'

SELECT username
FROM tenmo_user
JOIN account ON account.user_id = tenmo_user.user_id
WHERE account.user_id = 1001


SELECT username 
FROM tenmo_user 
JOIN account ON account.user_id = tenmo_user.user_id
JOIN transfer ON account.account_id = transfer.account_to
FROM transfer WHERE account.account_id = transfer.account_to

SELECT transfer_id, transfer_type_desc, transfer_status_desc, amount, user_to.username AS username_to, user_from.username AS username_from FROM transfer
JOIN account acc_from ON acc_from.account_id = transfer.account_from
JOIN account acc_to ON acc_to.account_id = transfer.account_to
JOIN tenmo_user user_from ON acc_from.user_id = user_from.user_id
JOIN tenmo_user user_to ON acc_to.user_id = user_to.user_id
JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id
JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id
WHERE transfer_id = ? AND
( user_from.username =? OR user_to.username =?)

SELECT * from transfer
JOIN account 



