import matplotlib.pyplot as plt
import numpy as np

# Dados brutos
sizes = [100, 200, 300, 400, 500, 600, 700]  # k
lexical_times = np.array([
    [49, 29, 15],  # 100k
    [28, 31, 25],  # 200k
    [37, 48, 30],  # 300k
    [42, 41, 45],  # 400k
    [87, 48, 64],  # 500k
    [115, 68, 90],  # 600k
    [88, 109, 69]  # 700k
])
frequency_times = np.array([
    [8, 2, 3],  # 100k
    [3, 3, 3],  # 200k
    [3, 3, 3],  # 300k
    [25, 19, 5],  # 400k
    [6, 6, 6],  # 500k
    [7, 8, 6],  # 600k
    [7, 9, 8]  # 700k
])

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
plt.title('Comparação dos tempos médios de execução por tamanho de arquivo')
plt.xticks(index + bar_width / 2, sizes)
plt.yticks(np.arange(0, np.max(lexical_means)+10, 10))
plt.grid(axis='y', linestyle='dashed')
plt.legend()
plt.tight_layout()
plt.show()
