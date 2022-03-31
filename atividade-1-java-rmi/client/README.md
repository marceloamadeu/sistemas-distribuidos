# Arquitetura Cliente-Servidor

## Modelo Publisher/Subscriber (Eventos e Notificações)

Aplicação para agendamento de reuniões em um departamento através de  enquetes


- Cada cliente tem um método para o recebimento de notificações assíncronas de eventos ocorridos no servidor:
  - O cliente que se cadastrou no sistema receberá uma notificação de evento do servidor, via chamada de método, quando uma nova enquete for cadastrada no sistema. Essa mensagem conterá informações sobre a enquete.
  - O cliente que participa (através do cadastro ou da votação) de uma enquete receberá uma notificação de evento do servidor, via chamada de método, quando uma enquete for encerrada (i.e., quando todos responderam ou quando a data limite expirar). Essa mensagem conterá informações sobre o resultado da enquete.