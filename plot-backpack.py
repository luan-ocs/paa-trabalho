import matplotlib.pyplot as plt
import numpy as np

# Dados brutos
knapsack_values = np.array([44.75, 44.875, 45.0, 52.111111111111114, 52.22222222222222, 
                            52.333333333333336, 52.44444444444444, 52.55555555555556, 
                            52.666666666666664, 52.77777777777778, 52.888888888888886, 
                            53.0, 61.142857142857146, 61.285714285714285, 61.42857142857143, 
                            61.57142857142857, 61.714285714285715, 61.857142857142854, 
                            62.0, 68.11111111111111, 68.22222222222223, 68.33333333333333, 
                            68.44444444444444, 68.55555555555556, 68.66666666666667, 
                            68.77777777777777, 68.88888888888889, 69.0, 77.33333333333333, 
                            77.66666666666667, 78.0])

# Número de execuções
executions = np.arange(1, len(knapsack_values) + 1)

plt.figure(figsize=(12, 6))

# Gráfico de barras
plt.bar(executions, knapsack_values, color='skyblue', width=0.6)
plt.title('Valor máximo da mochila para cada execução')
plt.xlabel('Execução')
plt.ylabel('Valor máximo da mochila')
plt.xticks(np.arange(1, len(knapsack_values) + 1))
plt.yticks(np.arange(0, np.max(knapsack_values) + 1, 5))
plt.grid(axis='y', linestyle='dashed')
plt.show()