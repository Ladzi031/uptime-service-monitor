package za.ac.ladzani.uptimeMonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UptimeMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UptimeMonitorApplication.class, args);
	}

	/*
	* TODO
	* on application crash notify?
	* set-up email stuff
	* keep referring to learn spring and spring essentials bro, this app will be public!
	* model different services(spring boot, node.js, laravel)
	* profile application memory and CPU footprint
	* */

	// consider having a dynamic feign client that returns a dynamic ResponseEntity<MAKE_ME_DYNAMIC> based on site used
	// this would allow us to persist PIN_LOGs that have serious importance compared to storing simple pings
}

/*
README stuff: If it’s mission-critical, use an existing service. You’ll save time and headaches.

If it's for personal projects or learning, build your own. It’s a great way to get into observability and devops tooling




✅ Build Your Own Tool

When it makes sense:

    You want to learn or experiment with monitoring infrastructure.

    You need custom checks (e.g., auth flows, specific content validation).

    You want full control over notifications, integrations, or data.

    You're avoiding external dependencies for privacy or cost reasons.

Pros:

    Tailored to your stack and needs.

    No recurring cost.

    Flexibility in handling outages or thresholds.
* */