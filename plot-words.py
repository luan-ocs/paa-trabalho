import matplotlib.pyplot as plt
import numpy as np

def ler_arquivo_numeros(nome_arquivo):
    vetor_list = []

    with open(nome_arquivo, 'r') as arquivo:
        for linha in arquivo:
            numeros = linha.strip().split(',')
            vetor = [float(numero) for numero in numeros]
            vetor_list.append(vetor)

    return vetor_list

# Dados brutos
sizes = [100, 200, 300, 400, 500, 600, 700]  # k
lexical_times = np.array(ler_arquivo_numeros("/home/abas/Desktop/personal/faculdade/paa/algorithms/out/alftime.txt"))
frequency_times = np.array(ler_arquivo_numeros("/home/abas/Desktop/personal/faculdade/paa/algorithms/out/freqtime.txt"))

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

# Exportar o gráfico como PNG
plt.savefig('grafico.png', dpi=400)
