# Experimental Setup in ns-2 for Testing TCP Variants

# Initialize the ns simulator
set ns [new Simulator]

# Define network nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Define links with configurable parameters
# High-latency link (e.g., satellite communication)
$ns duplex-link $n0 $n1 10Mb 500ms DropTail
# High packet loss link (e.g., wireless network)
$ns duplex-link $n1 $n2 5Mb 50ms DropTail
# High bandwidth-delay product link (e.g., optical networks)
$ns duplex-link $n2 $n3 100Mb 100ms DropTail

# Queue monitoring for fairness and congestion analysis
$ns queue-monitor $n0 $n1 "" queue-monitor-output-file
$ns queue-monitor $n1 $n2 "" queue-monitor-output-file
$ns queue-monitor $n2 $n3 "" queue-monitor-output-file

# Traffic generator setup for TCP flows
# TCP Tahoe flow
set tcpTahoe [new Agent/TCP/Tahoe]
$ns attach-agent $n0 $tcpTahoe
set sinkTahoe [new Agent/TCPSink]
$ns attach-agent $n1 $sinkTahoe
$ns connect $tcpTahoe $sinkTahoe
set ftpTahoe [new Application/FTP]
$ftpTahoe attach-agent $tcpTahoe
$ftpTahoe set type FTP

# TCP Reno flow
set tcpReno [new Agent/TCP/Reno]
$ns attach-agent $n1 $tcpReno
set sinkReno [new Agent/TCPSink]
$ns attach-agent $n2 $sinkReno
$ns connect $tcpReno $sinkReno
set ftpReno [new Application/FTP]
$ftpReno attach-agent $tcpReno
$ftpReno set type FTP

# TCP Cubic flow
set tcpCubic [new Agent/TCP/Cubic]
$ns attach-agent $n2 $tcpCubic
set sinkCubic [new Agent/TCPSink]
$ns attach-agent $n3 $sinkCubic
$ns connect $tcpCubic $sinkCubic
set ftpCubic [new Application/FTP]
$ftpCubic attach-agent $tcpCubic
$ftpCubic set type FTP

# TCP Vegas flow
set tcpVegas [new Agent/TCP/Vegas]
$ns attach-agent $n0 $tcpVegas
set sinkVegas [new Agent/TCPSink]
$ns attach-agent $n3 $sinkVegas
$ns connect $tcpVegas $sinkVegas
set ftpVegas [new Application/FTP]
$ftpVegas attach-agent $tcpVegas
$ftpVegas set type FTP

# Set simulation parameters
$ns at 0.1 "$ftpTahoe start"
$ns at 0.2 "$ftpReno start"
$ns at 0.3 "$ftpCubic start"
$ns at 0.4 "$ftpVegas start"

# End simulation after 20 seconds
$ns at 20.0 "finish"

# Define the finish procedure
proc finish {} {
    global ns
    $ns flush-trace
    exec nam output.nam &
    exec xgraph output.tr -geometry 800x400 &
    exit 0
}

# Run the simulation
$ns run
