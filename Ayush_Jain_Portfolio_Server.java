import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;


public class Ayush_Jain_Portfolio_Server {
    private static final int PORT = 8080; // change if needed
    private static final String PROFILE_IMAGE = "profile.jpg"; // put your photo here

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/profile.jpg", new FileHandler(PROFILE_IMAGE));
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Portfolio server started at http://localhost:" + PORT);
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = getHtml();
            byte[] bytes = html.getBytes("UTF-8");
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }

        private String getHtml() {
            // Full embedded HTML/CSS/JS for the animated portfolio (complete)
            return "<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <title>Ayush Jain - Portfolio</title>\n" +
                    "  <style>\n" +
                    "    /* Reset + base */\n" +
                    "    :root{--bg:#0f1724;--card:#0b1220;--accent1:#7c3aed;--accent2:#06b6d4;--glass: rgba(255,255,255,0.04);} \n" +
                    "    *{box-sizing:border-box}body{margin:0;font-family:Inter,system-ui,Segoe UI,Roboto,'Helvetica Neue',Arial;color:#e6eef8;background:linear-gradient(120deg,var(--bg),#071127);min-height:100vh;}\n" +
                    "    header{display:flex;align-items:center;justify-content:space-between;padding:18px 36px;position:sticky;top:0;backdrop-filter:blur(6px);background:linear-gradient(90deg, rgba(255,255,255,0.02), transparent);z-index:10}\n" +
                    "    .logo{display:flex;align-items:center;gap:12px} .logo .dot{width:42px;height:42px;border-radius:10px;background:linear-gradient(135deg,var(--accent1),var(--accent2));display:flex;align-items:center;justify-content:center;color:white;font-weight:700;font-size:14px} \n" +
                    "    nav a{color:rgba(230,238,248,0.85);text-decoration:none;margin-left:18px;font-weight:600} nav a:hover{opacity:0.85;transform:translateY(-2px)}\n" +
                    "    .hero{display:grid;grid-template-columns:1fr 420px;gap:40px;padding:60px 36px;align-items:center} \n" +
                    "    .hero-left h1{font-size:40px;margin:0;line-height:1.03} .muted{color:rgba(230,238,248,0.7)}\n" +
                    "    .type{color:var(--accent2);font-weight:700} \n" +
                    "    .cta{margin-top:18px;display:flex;gap:12px} .btn{padding:12px 18px;border-radius:12px;border:0;cursor:pointer;font-weight:700} .btn-primary{background:linear-gradient(90deg,var(--accent1),var(--accent2));color:white;box-shadow:0 8px 30px rgba(0,0,0,0.45)} .btn-ghost{background:transparent;border:1px solid rgba(255,255,255,0.06);color:rgba(230,238,248,0.9)}\n" +
                    "    .card{background:var(--card);padding:18px;border-radius:18px;box-shadow:0 10px 30px rgba(2,6,23,0.5)}\n" +
                    "    .profile{width:420px;height:420px;border-radius:20px;overflow:hidden;display:flex;align-items:center;justify-content:center;border:1px solid rgba(255,255,255,0.03)}\n" +
                    "    .profile img{width:100%;height:100%;object-fit:cover;transition:transform .9s cubic-bezier(.2,.9,.2,1)} .profile:hover img{transform:scale(1.06) rotate(1.5deg)}\n" +
                    "    /* animated gradient border */\n" +
                    "    .frame{padding:8px;border-radius:22px;background:linear-gradient(120deg, rgba(124,58,237,0.25), rgba(6,182,212,0.18));} \n" +
                    "    /* sections */\n" +
                    "    main{padding:36px} .grid{display:grid;grid-template-columns:repeat(2,1fr);gap:20px} .section-title{display:flex;align-items:center;gap:12px;margin-bottom:12px} .section-title h2{margin:0} \n" +
                    "    .about{display:flex;gap:20px;align-items:flex-start} .about .left{max-width:680px} .highlight{background:var(--glass);padding:12px;border-radius:12px} \n" +
                    "    /* skills */\n" +
                    "    .skill{margin-bottom:12px} .skill .label{display:flex;justify-content:space-between;font-weight:700;font-size:14px;margin-bottom:6px} .bar{height:12px;background: rgba(255,255,255,0.06);border-radius:999px;overflow:hidden} .bar > i{display:block;height:100%;width:0%;background:linear-gradient(90deg,var(--accent1),var(--accent2));border-radius:999px;transition:width 1.4s cubic-bezier(.2,.9,.2,1);} \n" +
                    "    /* projects carousel */\n" +
                    "    .carousel{position:relative;overflow:hidden;border-radius:12px} .slides{display:flex;gap:12px;transition:transform .6s ease;will-change:transform} .slide{min-width:320px;background:linear-gradient(180deg, rgba(255,255,255,0.02), transparent);padding:18px;border-radius:12px} .slide h3{margin:0} \n" +
                    "    .dots{display:flex;gap:8px;margin-top:12px;justify-content:center} .dot{width:10px;height:10px;border-radius:999px;background:rgba(255,255,255,0.06);cursor:pointer} .dot.active{background:linear-gradient(90deg,var(--accent1),var(--accent2))} \n" +
                    "    footer{padding:40px 36px;text-align:center;color:rgba(230,238,248,0.6)}\n" +
                    "    /* small screens */\n" +
                    "    @media(max-width:900px){.hero{grid-template-columns:1fr;}.profile{width:320px;height:320px;margin:0 auto}.grid{grid-template-columns:1fr}.hero-left h1{font-size:34px}}\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <header>\n" +
                    "    <div class=\"logo\">\n" +
                    "      <div class=\"dot\">AJ</div>\n" +
                    "      <div>\n" +
                    "        <div style=\"font-weight:800\">Ayush Jain</div>\n" +
                    "        <div style=\"font-size:12px;color:rgba(230,238,248,0.6)\">Developer • Designer • Student</div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "    <nav>\n" +
                    "      <a href=\"#about\">About</a>\n" +
                    "      <a href=\"#skills\">Skills</a>\n" +
                    "      <a href=\"#projects\">Projects</a>\n" +
                    "      <a href=\"#contact\">Contact</a>\n" +
                    "    </nav>\n" +
                    "  </header>\n" +
                    "  <section class=\"hero\">\n" +
                    "    <div class=\"hero-left\">\n" +
                    "      <h1>Hello, I'm <span style=\"background:linear-gradient(90deg,var(--accent1),var(--accent2));-webkit-background-clip:text;background-clip:text;color:transparent;\">Ayush Jain</span></h1>\n" +
                    "      <p class=\"muted\">“I’m an Electronics and Telecommunication Engineering student and budding software developer who loves building beautiful UIs, animations, and small full-stack projects.”</p>\n" +
                    "      <div style=\"margin-top:10px;font-size:18px\">\n" +
                    "        <span class=\"muted\">I build</span> <span class=\"type\" id=\"typed\">interactive websites</span>\n" +
                    "      </div>\n" +
                    "      <div class=\"cta\">\n" +
                    "        <button class=\"btn btn-primary\" onclick=\"location.href='#contact'\">Hire / Contact</button>\n" +
                    "        <button class=\"btn btn-ghost\" onclick=\"window.open('https://github.com/Ayushjain45','_blank')\">View GitHub</button>\n" +
                    "      </div>\n" +
                    "      <div style=\"margin-top:18px;display:flex;gap:12px;flex-wrap:wrap\">\n" +
                    "        <div class=\"card highlight\">\n" +
                    "          <strong>EDUCATION:</strong><br/> St.attri senior secondry public school\n" +
                    "        </div>\n" +
                    "        <div class=\"card highlight\">\n" +
                    "          <strong>Current Education:</strong><br/> B.Tech (ELECTRONICS AND TELECOMMUNICATION) - (Chandigrah group of colleges)\n" +
                    "        </div>\n" +
                    "        <div class=\"card highlight\">\n" +
                    "          <strong>Email:</strong><br/> ayushjain00111@gmail.com\n" +
                    "        </div>\n" +
                    "        <div class=\"card highlight\">\n" +
                    "          <strong>Contact:</strong><br/> 8054220702\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"frame\">\n" +
                    "      <div class=\"profile card\">\n" +
                    "        <img src=\"/profile.jpg\" alt=\"Ayush Jain\">\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </section>\n" +
                    "  <main>\n" +
                    "    <section id=\"about\" class=\"card\">\n" +
                    "      <div class=\"section-title\"><h2>About Me</h2></div>\n" +
                    "      <div class=\"about\">\n" +
                    "        <div class=\"left\">\n" +
                    "          <p>“My name is Ayush Jain, an Electronics and Telecommunication Engineering student with a growing passion for coding and technology. While my academic focus is on electronics and communication, I love building projects in Java, web design, and dynamic UI animations. Outside academics, I enjoy music, problem-solving, and creative design.” </p>\n" +
                    "          <p><strong>Personal Interests:</strong>  Music, battle royal, competitive programming, singing.</p>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "          <div class=\"card\">\n" +
                    "            <h3>Contact</h3>\n" +
                    "            <p><strong>Email:</strong> ayushjain00111@gmail.com<br/><strong>Phone:</strong> 8054220702</p>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </section>\n" +
                    "    <section id=\"skills\" style=\"margin-top:18px\" class=\"card\">\n" +
                    "      <div class=\"section-title\"><h2>Technical Skills</h2></div>\n" +
                    "      <div class=\"grid\">\n" +
                    "        <div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>Java</span><span>90%</span></div><div class=\"bar\"><i data-width=\"90%\"></i></div></div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>Spring Boot</span><span>78%</span></div><div class=\"bar\"><i data-width=\"78%\"></i></div></div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>HTML/CSS</span><span>88%</span></div><div class=\"bar\"><i data-width=\"88%\"></i></div></div>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>JavaScript</span><span>82%</span></div><div class=\"bar\"><i data-width=\"82%\"></i></div></div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>SQL</span><span>70%</span></div><div class=\"bar\"><i data-width=\"70%\"></i></div></div>\n" +
                    "          <div class=\"skill\"><div class=\"label\"><span>Git</span><span>80%</span></div><div class=\"bar\"><i data-width=\"80%\"></i></div></div>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </section>\n" +
                    "    <section id=\"projects\" style=\"margin-top:18px\" class=\"card\">\n" +
                    "      <div class=\"section-title\"><h2>Projects</h2></div>\n" +
                    "      <div class=\"carousel\">\n" +
                    "        <div class=\"slides\" id=\"slides\">\n" +
                    "          <div class=\"slide\">\n" +
                    "            <h3>College Food Ordering System</h3><p>Full-stack app with Java backend, animated UI, and image gallery.</p>\n" +
                    "          </div>\n" +
                    "          <div class=\"slide\">\n" +
                    "            <h3>Mini Spotify</h3><p>Music streaming UI with animated equalizer and playlist support.</p>\n" +
                    "          </div>\n" +
                    "          <div class=\"slide\">\n" +
                    "            <h3>Portfolio Server</h3><p>This single-file, runnable portfolio server (Java + lightweight HTTP) — you're viewing it now!</p>\n" +
                    "          </div>\n" +
                    "          <div class=\"slide\">\n" +
                    "            <h3>Twitter Sentiment Analyzer</h3><p>A small ML dashboard using Streamlit and classical models for sentiment classification.</p>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "      <div class=\"dots\" id=\"dots\"></div>\n" +
                    "    </section>\n" +
                    "    <section id=\"contact\" style=\"margin-top:18px\" class=\"card\">\n" +
                    "      <div class=\"section-title\"><h2>Contact Me</h2></div>\n" +
                    "      <form onsubmit=\"alert('This is a demo. Replace with server-side handling if you want to receive messages.');return false;\">\n" +
                    "        <div style=\"display:flex;gap:12px;flex-wrap:wrap\">\n" +
                    "          <input placeholder=\"Ayush jain\" style=\"flex:1;padding:12px;border-radius:10px;border:1px solid rgba(255,255,255,0.04);background:transparent;color:inherit\">\n" +
                    "          <input placeholder=\"ayushjain00111@gmail.com\" style=\"flex:1;padding:12px;border-radius:10px;border:1px solid rgba(255,255,255,0.04);background:transparent;color:inherit\">\n" +
                    "        </div>\n" +
                    "        <textarea placeholder=\"Message\" style=\"width:100%;margin-top:12px;padding:12px;border-radius:10px;border:1px solid rgba(255,255,255,0.04);background:transparent;color:inherit\"></textarea>\n" +
                    "        <div style=\"margin-top:12px;text-align:right\"><button class=\"btn btn-primary\" type=\"submit\">Send Message</button></div>\n" +
                    "      </form>\n" +
                    "    </section>\n" +
                    "  </main>\n" +
                    "  <footer>Designed & built by Ayush Jain • © " + java.time.Year.now().getValue() + "</footer>\n" +
                    "  <script>\n" +
                    "    // Simple typed effect (no external libs)\n" +
                    "    (function(){\n" +
                    "      const words = ['interactive websites','beautiful UI', 'animated experiences','clean code'];\n" +
                    "      let idx = 0, i = 0, forward = true;\n" +
                    "      const el = document.getElementById('typed');\n" +
                    "      function tick(){\n" +
                    "        const word = words[idx];\n" +
                    "        if(forward){ i++; if(i>word.length){ forward=false; setTimeout(tick,800); return; } } else { i--; if(i<0){ forward=true; idx=(idx+1)%words.length; setTimeout(tick,200); return; } }\n" +
                    "        el.textContent = word.slice(0,i);\n" +
                    "        setTimeout(tick, 80 + Math.random()*60);\n" +
                    "      }\n" +
                    "      tick();\n" +
                    "    })();\n" +
                    "    // animate skill bars when visible\n" +
                    "    (function(){\n" +
                    "      function animateBars(){\n" +
                    "        document.querySelectorAll('.bar > i').forEach(i=>{ i.style.width = i.getAttribute('data-width'); });\n" +
                    "      }\n" +
                    "      const obs = new IntersectionObserver(entries=>{ entries.forEach(e=>{ if(e.isIntersecting){ animateBars(); obs.disconnect(); } }); },{threshold:0.2});\n" +
                    "      const el = document.getElementById('skills'); if(el) obs.observe(el); else animateBars();\n" +
                    "    })();\n" +
                    "    // simple carousel\n" +
                    "    (function(){\n" +
                    "      const slides = document.getElementById('slides'); const dots = document.getElementById('dots');\n" +
                    "      if(!slides || !dots) return;\n" +
                    "      const count = slides.children.length; let pos=0;\n" +
                    "      for(let i=0;i<count;i++){ const d=document.createElement('div'); d.className='dot'; d.onclick=(()=>{pos=i; update();}); dots.appendChild(d);} \n" +
                    "      function update(){ slides.style.transform = 'translateX(' + (-pos*(320+12)) + 'px)'; Array.from(dots.children).forEach((dd,ii)=> dd.classList.toggle('active', ii===pos)); }\n" +
                    "      setInterval(()=>{ pos=(pos+1)%count; update(); }, 3500); update();\n" +
                    "    })();\n" +
                    "  </script>\n" +
                    "</body>\n" +
                    "</html>\n";
        }
    }

    static class FileHandler implements HttpHandler {
        private final Path file;

        FileHandler(String filename) {
            this.file = Path.of(filename);
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!Files.exists(file)) {
                // respond with a placeholder SVG if profile image missing
                String svg = "<svg xmlns='http://www.w3.org/2000/svg' width='800' height='800'><rect width='100%' height='100%' fill='#0b1220'/><text x='50%' y='50%' fill='#7c3aed' font-size='28' text-anchor='middle' dominant-baseline='middle'>Put profile.jpg in the same folder</text></svg>";
                byte[] b = svg.getBytes("UTF-8");
                exchange.getResponseHeaders().add("Content-Type", "image/svg+xml; charset=utf-8");
                exchange.sendResponseHeaders(200, b.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(b); }
                return;
            }
            String type = Files.probeContentType(file);
            if (type == null) type = "application/octet-stream";
            exchange.getResponseHeaders().add("Content-Type", type);
            byte[] bytes = Files.readAllBytes(file);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
        }
    }
}

