# Comunicação Multicast
- Construído sobre o protocolo IP.
- O IP multicast está disponível somente via UDP.
- Permite ao emissor transmitir um único datagrama para um conjunto de computadores que formam o grupo multicast.
- Emissor não conhece as identidades dos receptores individuais e nem o tamanho do grupo.
- Um grupo é especificado por um IP da Classe D
  - 1o octeto = 1110xxxx em IPv4;
  - 224.0.0.0 a 239.255.255.255.