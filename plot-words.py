import os
import matplotlib.pyplot as plt
import numpy as np

def read_numbers_file(file_name):
    aux = []

    with open(file_name, 'r') as file:
        for line in file:
            numbers = line.strip().split(',')
            array = [float(number) for number in numbers]
            aux.append(array)

    return aux

# Diretório atual
current_dir = os.getcwd()

# Dados brutos
sizes = [100, 200, 300, 400, 500, 600, 700]  # k
lexical_times = np.array(read_numbers_file(os.path.join(current_dir, "out", "alftime.txt")))
frequency_times = np.array(read_numbers_file(os.path.join(current_dir, "out", "freqtime.txt")))
execution_quantity = len(lexical_times[0])
chart_title = f'Comparação dos tempos médios de execução por quantidade de palavras para {execution_quantity} {"execuções" if execution_quantity > 1 else "execução"}'
chart_file_name = f'out/grafico-ordenacao-{execution_quantity}-{"execucoes" if execution_quantity > 1 else "execucao"}.png'

# Médias e desvios padrão
lexical_means = np.mean(lexical_times, axis=1)
lexical_std = np.std(lexical_times, axis=1)
frequency_means = np.mean(frequency_times, axis=1)
frequency_std = np.std(frequency_times, axis=1)

bar_width = 0.35
index = np.arange(len(sizes))

# Gráfico
plt.figure(figsize=(12, 6))
bar1 = plt.bar(index, lexical_means, bar_width, yerr=lexical_std, alpha=0.7, capsize=7, label='Lexicográfica')
bar2 = plt.bar(index + bar_width, frequency_means, bar_width, yerr=frequency_std, alpha=0.7, capsize=7, label='Frequência')

plt.xlabel('Tamanho do arquivo (k)')
plt.ylabel('Tempo de execução (ms)')
plt.title(chart_title)
plt.xticks(index + bar_width / 2, sizes)
plt.yticks(np.arange(0, np.max(lexical_means)+10, 10))
plt.grid(axis='y', linestyle='dashed')
plt.legend()
plt.tight_layout()

# Exportar o gráfico como PNG
plt.savefig(chart_file_name, dpi=400)
