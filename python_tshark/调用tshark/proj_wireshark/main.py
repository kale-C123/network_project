from capture_analysis import capture_packets, analyze_packets

if __name__ == "__main__":
    interface = "以太网"  # Windows 用户可改为 "Wi-Fi" 或 "Ethernet"
    capture_time = 10  # 抓包时长

    # 抓包
    capture_packets(interface=interface, duration=capture_time)

    # 解析数据包
    analyze_packets()
