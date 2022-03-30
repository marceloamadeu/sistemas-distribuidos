# Comunicação UDP

- Protocolo não orientado a conexão.
- Transporte de dados não confiável.
- Emprega send não-bloqueante e receive bloqueante.
- Mensagem de entrada é inserida em uma fila.
- Mensagens são retiradas da fila por invocações de receive no socket. 
- Método receive bloqueia até que um datagrama seja recebido, a menos que um temporizador seja configurado no socket.
- Método receive retorna o endereço IP e a porta do processo emissor junto com a mensagem.
