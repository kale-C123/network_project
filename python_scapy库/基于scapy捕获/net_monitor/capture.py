from scapy.layers.inet import IP, TCP
from scapy.sendrecv import sniff
import pandas as pd
import os
from argparse import ArgumentParser  # 新增：支持命令行参数
import time

# 协议号转名称的映射字典（新增）
PROTOCOL_MAP = {
    1: "ICMP",#ICMP
    6: "TCP",#TCP
    17: "UDP",#UDP
    # 可扩展其他协议
}


def packet_handler(packet, output_file):
    """处理捕获的数据包并保存到CSV"""
    if IP in packet:
        # 提取数据
        src_ip = packet[IP].src
        dst_ip = packet[IP].dst
        proto_num = packet[IP].proto
        proto_name = PROTOCOL_MAP.get(proto_num, f"Unknown({proto_num})")  # 协议号转名称
        length = len(packet)
        timestamp = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(packet.time))  # 时间戳格式化

        # 构建数据行
        data_row = [timestamp, src_ip, dst_ip, proto_name, length]

        # 写入CSV（优化为批量写入）
        df = pd.DataFrame([data_row], columns=["Timestamp", "Source", "Destination", "Protocol", "Length"])
        df.to_csv(output_file, mode='a', header=False, index=False)
        print(f"已记录: {data_row}")  # 调试输出


def main():
    # 命令行参数解析（新增）
    parser = ArgumentParser(description="网络流量抓取工具")
    parser.add_argument("-f", "--filter", default="tcp", help="抓包过滤器（BPF语法），默认：ip")
    parser.add_argument("-o", "--output", default="network_traffic.csv", help="输出CSV文件名，默认：network_traffic.csv")
    parser.add_argument("-t", "--timeout", type=int, default=20, help="抓包时长（秒），默认：20")
    args = parser.parse_args()

    # 初始化CSV文件（写入表头）
    # 仅在文件不存在时写入表头
    if not os.path.isfile(args.output):
        header_df = pd.DataFrame(columns=["Timestamp", "Source", "Destination", "Protocol", "Length"])
        header_df.to_csv(args.output, index=False)


    print(f"开始抓包，过滤器：'{args.filter}'，时长：{args.timeout}秒...")
    sniff(filter=args.filter, prn=lambda pkt: packet_handler(pkt, args.output), timeout=args.timeout)
    print(f"抓包完成！数据已保存到：{args.output}")


if __name__ == "__main__":
    main()