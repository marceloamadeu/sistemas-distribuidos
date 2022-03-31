# Arquitetura Cliente-Servidor

## Modelo Publisher/Subscriber (Eventos e Notificações)

Aplicação para agendamento de reuniões em um departamento através de  enquetes

Métodos disponíveis no servidor:
- Cadastro usuário:
  - o Ao acessar o sistema pela primeira vez, cada cliente deve informar seu nome, chave pública e sua referência de objeto remoto. Nesse cadastro, o cliente  automaticamente atuará como subscriber, registrando interesse em receber notificações do servidor quando uma nova enquete for cadastrada.

- Cadastro de enquete:
  - o Cliente deve informar: seu nome, título da enquete, local do evento, propostas de tempo (datas e horários), data limite para obter respostas. Nesse cadastro, o cliente automaticamente atuará como subscriber, registrando interesse em receber notificações do servidor quando essa enquete for finalizada.
  - Em cada cadastro de enquete, o servidor atuará como publisher e enviará uma  notificação assíncrona aos clientes avisando sobre esse novo evento. Essa  notificação se dará na forma de uma chamada de método do servidor para o cliente.
  - Para isso, o servidor utilizará as referências de objeto remoto dos clientes que se cadastraram.

- Cadastro de voto em uma enquete:
  - Ao receber uma notificação de uma nova enquete, o cliente deve votar nessa enquete. Para isso, ele deve informar: seu nome, título da enquete e as  propostas de tempo das quais ele pode participar. Nesse cadastro, o cliente automaticamente atuará como subscriber, registrando interesse em receber notificações do servidor quando essa enquete for finalizada.

- Consulta de enquete:
  - Cliente deve informar o seu nome e o título da enquete. Essa mensagem de consulta deve conter uma assinatura digital. Para isso, o cliente utilizará a sua chave privada. 
  O servidor receberá a mensagem e validará a assinatura utilizando a chave pública correspondente que foi fornecida no cadastro. O servidor retornará as informações desejadas sobre o andamento da enquete (participantes que já votaram, propostas votadas, estado da enquete (andamento ou encerrada)) apenas se a assinatura for válida e se o cliente tiver permissão para acessar a enquete (i.e., estiver participando da enquete). Caso contrário, servidor retornará “permissão negada”.
- Quando uma enquete for finalizada (i.e., quando todos responderam ou quando a data limite expirar), o servidor atuará como publisher e enviará uma notificação assíncrona aos clientes avisando sobre esse evento. Essa notificação se dará na forma de uma chamada de método do servidor para o cliente. Para isso, o servidor utilizará as referências de objeto remoto dos clientes que se cadastraram e que
participaram (i.e., votaram) da enquete.

Observações:
- Desenvolva uma interface com recursos de interação apropriados;
- Adicione comentários no código explicando os métodos de sua aplicação;
- É obrigatória a defesa da aplicação para obter a nota.
- O desenvolvimento da aplicação pode ser individual ou em dupla. Porém, a defesa da aplicação é individual.