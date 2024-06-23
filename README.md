# Kindlet Template

This repository act's as a template for developing a Kindlet (Java applet for amazon kindles). This is targeting Kindlet 1.3, for Kindle 4th gen, but you can tweak it to work for touch devices or 1.0.

This was made for Linux, it might work under WSL for windows. Unsure about mac.

## Deps

- [Ant](https://ant.apache.org/) (Any version should work)
- [JDK 1.5](https://www.oracle.com/uk/java/technologies/java-archive-javase5-downloads.html) (Download and unpack the respected version, you do not need to make it your default java)
- [sshpass](https://linux.die.net/man/1/sshpass) (For deploying)
- [iproute2](https://en.wikipedia.org/wiki/Iproute2) (For connecting to USBNET)
- [A jailbroken kindle, with USBNET](https://www.mobileread.com/forums/showthread.php?t=225030) (How else do you expect to test your app?)


## Setup
### Connect your kindle
Find a micro-B cable that has a data path, and plug it into your PC.

Open a terminal, and type `ip addr`

``
4: enp7s0f3u3: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UNKNOWN group default qlen 1000
    link/ether ee:49:00:00:00:00 brd ff:ff:ff:ff:ff:ff
``

You should see a device like this. Grab its name (in this case its `enp7s0f3u3`, but it may be named `USB0` or something else)

Next, you'll need to give the kindle an address with a netmask of 24. This will allow us to ping & ssh into the device.

`sudo ip addr add 192.168.15.204/24 dev enp7s0f3u3`

Once done, try to ping or ssh into `192.168.15.244` (not a typo, the two addresses are different).

### Grab your Kindlet jar file

SSH into your kindle `ssh root@192.168.15.244` and type any password, assuming you haven't set a password, the kindle will accept anything.

Head over to `/opt/amazon/ebook/lib/` and find the ``Kindlet-*.*.jar`` file. You'll want to copy it over to your `lib` folder. 

Depending on your kindle, you will find one of three files:

- Kindlet-1.0.jar
- Kindlet-1.3.jar
- Kindlet-2.0.jar

1.0 and 1.3 are pretty similar to one another, a Kindle using 1.3 can run 1.0, but not vice versa. 1.x and 2.x are not compatible (2.0 is used on the touch screen).

If you are targeting touch devices, remove the src/com/amazon folder and look at community examples of how the 2.0 API works.

### Build
You'll need to do is edit the `kindlet.properties` file to point to your JDK 1.5 bin folder. 

<details>
  <summary>Why can't I use a modern JDK version?</summary>
  
  Modern JDK only only support java 8 and above. Kindles (4th gen and below) only support 1.4 and below (but even then, a lot of 1.4 features don't work)

  We download an older JDK and target 1.5, and then use retroweaver to backport our 1.5 program to 1.4. This should mean more 1.5 features work, however, the kindle is a fragile thing and will crash at runtime with certain features.
</details>

Once done, run `ant deploy` and everything should work fine! You may want to read the amazon docs by running `ant doc`.

## Next steps

If you plan to share your kindlets, use [KindleTool](https://www.mobileread.com/forums/showthread.php?t=187880) to make it easier to share. 

- Read [Mobileread wiki on kindle hacks](https://wiki.mobileread.com/wiki/Kindle_Hacks_Information)
- Read [Mobileread wiki on kindlet development](https://wiki.mobileread.com/wiki/Kindlet_Developer_HowTo)
- Use [Kindlet Jailbreak](https://bitbucket.org/ixtab/kindletjailbreak/src) to escape the sandbox environment

- Use [JNI](https://www.mobileread.com/forums/showthread.php?t=175899) to escape the sandbox and or expand what you can do

- Using [FBInk](https://github.com/NiLuJe/FBInk) to print text and images directly to the framebuffer

- Using [Linux framebuffer](https://en.wikipedia.org/wiki/Linux_framebuffer) to avoid using java

## Caveats

The kindles CVM (compact virtual machine) is very fragile. There are a lot of missing features.

### Not implimented
- ``+=``| use ``.concat()`` instead
- ``.valueOf`` | use ``new Integer()`` instead
- A lot of java.awt functions do not work
- enums cant be backported
- Avoid doing any network calls, their threads often cause crashes, and it does not support modern SSL standards (uses TLS 1.0/1.1). You can work around this by using JNI with a modern library
- Avoid using callbacks in JNI threads, it wont crash but it will cause the UI to ignore all user inputs in that new thread
- CVM will crash every now and then because of a bug that triggers GNU LIBC run-time protections. You can fix this by adding `MALLOC_CHECK_=0` to your `start.sh` file in `/opt/amazon/ebook/bin/start.sh`

### Debugging
You'll want to keep an eye on ``/var/log/messages`` and ``/mnt/us/development/APP_NAME/work/crash.log``. The messages you get will often not be very verbose.

## Credits
This repo was made using public resources, none of which has been ripped out the kindle directly or from other proprietary sources.

I made this as a resource for others to use as there is not much on compiling a Kindlet using modern tools. 

- [Cowlark](http://cowlark.com/kindle/index.html)
- [Mobileread](https://www.mobileread.com/forums/forumdisplay.php?f=150)
- [SixFoisNeuf](https://www.sixfoisneuf.fr/posts/kindle-hacking-porting-doom/)



