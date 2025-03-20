import pandas as pd
import matplotlib.pyplot as plt

# 读取数据
df = pd.read_csv("network_traffic.csv", names=["Timestamp", "Source", "Destination", "Protocol", "Length"],
                 skiprows=1)

# 统计协议分布
protocol_counts = df["Protocol"].value_counts()
print("协议分布：\n", protocol_counts.to_string(header=False))

# 检测异常流量（长度超过平均值的2倍标准差）
mean_length = df["Length"].mean()
std_length = df["Length"].std()
df["Is_Anomaly"] = df["Length"] > (mean_length + 2 * std_length)

df["CSV_Line"] = df.index + 2  # 记录原文件行号

# 输出异常流量
anomalies = df[df["Is_Anomaly"]]
print("\n异常流量记录：\n", anomalies.to_string(max_rows=None,max_colwidth=50))   # 每列最大宽度（避免文本截断）

# 可视化流量时序
plt.figure(figsize=(10, 4))
plt.scatter(df["Timestamp"], df["Length"], c=df["Is_Anomaly"], cmap="viridis")
plt.title("Network Traffic Over Time (Anomalies Highlighted)")
plt.xlabel("Timestamp")
plt.ylabel("Packet Length")
plt.savefig("traffic_plot.png")  # 保存图表