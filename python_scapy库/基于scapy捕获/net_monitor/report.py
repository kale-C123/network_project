from datetime import datetime
from analyze import anomalies,df

def generate_report():
    with open("network_report.txt", "w") as f:
        f.write(f"Network Traffic Report - {datetime.now()}\n")
        f.write("----------------------------------------\n")
        f.write("1. 抓包总数: {}\n".format(len(df)))
        f.write("2. 异常流量数量: {}\n".format(len(anomalies)))
        f.write("3. 异常流量详情:\n")
        f.write(anomalies.to_string(index=False))

# 运行分析脚本后调用
generate_report()