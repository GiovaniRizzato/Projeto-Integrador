#Processos
1 - Estrutura do processo<br />
A classe processo é divido em 3 classes (classe "Processo" possui 3 atributos privados do tipo classe): Contexto de software, que contem toda a parte de gerenciamento de informações sobre o estado atual do processo como prioridade, instrução atual no programa e PID. Outra classe seria o contexto de memoria, onde gurda informações sobre  dados que, no caso real, estariam na memoria do computador como sequência de instruções, (no código esta com nome de programa) e quantidade de memoria usada.

2 - Estados do processo<br />
Através dos filtros da lista, ou seja, todos os processos são armazenados em uma lista normal e ao ser necessário o agrupamento por prioridades e esperas de entrada e saída, é aplicado uma operação que tem como saída filas de processos agrupados nesta forma.

3 - Mudança de estado do processo<br />
No caso de pronto para execução e vice-versa o simulador pega a fila que irá ser processada (gerada a partir dos filtros) e à executa coloca em um espaço de memoria fixo para processos em execução, e executa o número "x" de instruções que aquele processo tem direito, que o coloca na na fila novamente de que foi tirado, se for o caso de continuar no mesmo processo. Caso o processo preciso trocar de estado, ou seja, trocar de fila, ele é enviado novamente a lista principal onde apenas no proximo ciclo será envado a outra fila.

4 - Criação e eliminação de processos<br />
Para criação foi feita uma rotina que cria um processo em um ponteiro fixo e o coloca na lista de processos principais.

Para finalização, o programa sobre escreve a instrução atual do programa por uma instrução de "fim", que ao próximo estagio de execução que ele tiver chance de fazer, irá ser feito a coleta dos dados estatísticos e eliminação processo da memoria.

5 - Processos CPU-bound e I/O-bound<br />
Não há nenhum implementação quanto a otimização ou reconhecimento deles.

6 - Processos Fowrodground e Background<br />
Não Implementado, uma vez que todos os processos, mesmo que backgorund, tem o mesmo impacto no escalonador de processo.

7 - Formas de criação de processos<br />
Para criação foi feita uma rotina que cria um processo em um ponteiro fixo e o coloca na lista de processos principais.

8 - Processos independentes, subprocessos e threds<br />
Nada foi implementado neste sentido.

9 - Processos do sistema operacional<br />
Nada foi implementado neste sentido.

#Memoria
Será implementdo na alocação de memoria, uma estratégia de <i>First-fit</i>, pois utiliza pouco recurso computacional para implementação e possui uma certa organização de allocação, de forma que as partições menores fiquem nas posições mais baixas, deixando as maiores para as patições maiores , e por consequencia mais dificenis de serem encaixadas, nas posições mais altas.
