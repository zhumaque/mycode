The boot loader is the first piece of software started by the BIOS or UEFI. It is responsible for loading the kernel with the wanted kernel parameters, and initial RAM disk based on config files.
Note: Loading Microcode updates requires adjustments in boot loader configuration. [1]
Feature comparison
Note:
Boot loaders only need to support the file system on which kernel and initramfs reside (the file system on which /boot is located).
As GPT is part of the UEFI specification, all UEFI boot loaders support GPT disks. GPT on BIOS systems is possible, using either "hybrid booting" with Hybrid MBR, or the new GPT-only protocol. This protocol may however cause issues with certain BIOS implementations; see rodsbooks for details.
Encryption mentioned in file system support is filesystem-level encryption, it has no bearing on block-level encryption.
Name	Firmware	Partition table	Multi-boot	File systems	Notes
BIOS	UEFI	MBR	GPT	Btrfs	ext4	ReiserFS	VFAT	XFS
EFISTUB	–	Yes	Yes	Yes	–	–	–	–	ESP only	–	Kernel turned into EFI executable to be loaded directly from UEFI firmware or another boot loader.
Clover	emulates UEFI	Yes	Yes	Yes	Yes	No	without encryption	No	Yes	No	Fork of rEFIt modified to run macOS on non-Apple hardware.
GRUB	Yes	Yes	Yes	Yes	Yes	without zstd compression	Yes	Yes	Yes	Yes	On BIOS/GPT configuration requires a BIOS boot partition. 
Supports RAID, LUKS1 and LVM (but not thin provisioned volumes).
rEFInd	No	Yes	Yes	Yes	Yes	without: encryption, zstd compression	without encryption	without tail-packing feature	Yes	No	Supports auto-detecting kernels and parameters without explicit configuration.
Syslinux	Yes	Partial	Yes	Yes	Partial	without: multi-device volumes, compression, encryption	without encryption	No	Yes	MBR only; without sparse inodes	No support for certain file system features [2] 
The boot loader can only access the file system it is installed to.[3]
systemd-boot	No	Yes	Manual install only	Yes	Yes	No	No	No	ESP only	No	Cannot launch binaries from partitions other than ESP.
GRUB Legacy	Yes	No	Yes	No	Yes	No	No	Yes	Yes	v4 only	Discontinued in favor of GRUB.
LILO	Yes	No	Yes	No	Yes	No	without encryption	Yes	Yes	Yes	Discontinued due to limitations (e.g. with Btrfs, GPT, RAID).
See also Wikipedia:Comparison of boot loaders.
Kernel
The kernel is the core of an operating system. It functions on a low level (kernelspace) interacting between the hardware of the machine and the programs which use the hardware to run. To make efficient use of the CPU, the kernel uses a scheduler to arbitrate which tasks take priority at any given moment, creating the illusion of many tasks being executed simultaneously.
initramfs
After the bootloader loads the kernel and possible initramfs files and executes the kernel, the kernel unpacks the initramfs (initial RAM filesystem) archives into the (then empty) rootfs (initial root filesystem, specifically a ramfs or tmpfs). The first extracted initramfs is the one embedded in the kernel binary during the kernel build, then possible external initramfs files are extracted. Thus files in the external initramfs overwrite files with the same name in the embedded initramfs. The kernel then executes /init (in the rootfs) as the first process. The early userspace starts.
Arch Linux uses an empty archive for the builtin initramfs (which is the default when building Linux). See mkinitcpio for more and Arch-specific info about the external initramfs.
The purpose of the initramfs is to bootstrap the system to the point where it can access the root filesystem (see FHS for details). This means that any modules that are required for devices like IDE, SCSI, SATA, USB/FW (if booting from an external drive) must be loadable from the initramfs if not built into the kernel; once the proper modules are loaded (either explicitly via a program or script, or implicitly via udev), the boot process continues. For this reason, the initramfs only needs to contain the modules necessary to access the root filesystem; it does not need to contain every module one would ever want to use. The majority of modules will be loaded later on by udev, during the init process.
Init process
At the final stage of early userspace, the real root is mounted, and then replaces the initial root filesystem. /sbin/init is executed, replacing the /init process. Arch uses systemd as the default init.
Getty
init calls getty once for each virtual terminal (typically six of them), which initializes each tty and asks for a username and password. Once the username and password are provided, getty checks them against /etc/passwd and /etc/shadow, then calls login. Alternatively, getty may start a display manager if one is present on the system.
Display manager
Tango-edit-clear.pngThis article or section needs language, wiki syntax or style improvements. See Help:Style for reference.Tango-edit-clear.png
Reason: A display manager is also a GUI, see #GUI, xinit or wayland (Discuss in Talk:Arch boot process#)
A display manager can be configured to replace the getty login prompt on a tty.
Login
The login program begins a session for the user by setting environment variables and starting the user's shell, based on /etc/passwd.
The login program displays the contents of /etc/motd (message of the day) after a successful login, just before it executes the login shell. It is a good place to display your Terms of Service to remind users of your local policies or anything you wish to tell them.
Shell
Once the user's shell is started, it will typically run a runtime configuration file, such as bashrc, before presenting a prompt to the user. If the account is configured to Start X at login, the runtime configuration file will call startx or xinit.
GUI, xinit or wayland
xinit runs the user's xinitrc runtime configuration file, which normally starts a window manager. When the user is finished and exits the window manager, xinit, startx, the shell, and login will terminate in that order, returning to getty.
