import subprocess
import pandas as pd
from scapy.all import rdpcap


def capture_packets(interface, duration=10, output_file="capture.pcap"):
    """
    使用 Tshark 进行实时抓包
    """
    command = [
        "tshark",
        "-i", interface,  # 指定网络接口（Windows 可使用 "Wi-Fi" 或 "Ethernet"）
        "-a", f"duration:{duration}",  # 设置抓包时间
        "-w", output_file  # 输出文件
    ]

    print(f"开始抓包 {duration} 秒...")
    subprocess.run(command, shell=True, check=True)
    print(f"抓包完成，文件保存在 {output_file}")


def analyze_packets(pcap_file="capture.pcap"):
    """
    使用 Scapy 解析 pcap 文件，并提取关键信息
    """
    packets = rdpcap(pcap_file)

    data = []
    for pkt in packets:
        if pkt.haslayer("IP"):
            data.append({
                "src": pkt["IP"].src,
                "dst": pkt["IP"].dst,
                "protocol": pkt["IP"].proto,
                "length": len(pkt)
            })

    df = pd.DataFrame(data)
    print(df.head())  # 打印前几行数据
    df.to_csv("packet_analysis.csv", index=False)
    print("数据包解析完成，结果保存在 packet_analysis.csv")

