**Protein Analysis Dashboard**
The Protein Analysis Dashboard is a full-stack web application that enables users to analyze protein sequences and visualize their 3D structures interactively.

Users can input a UniProt ID or PDB ID to:
- Fetch protein data using **UniProt / PDB IDs**
- Visualize protein structures in **interactive 3D**
- Perform **mutation analysis**
- Understand **real-world disease relevance**
- Analyze **amino acid composition**

- This project focuses on **real-world biological use cases**, especially how mutations impact protein structure and function.

**Key Features:**
Protein Analysis
- Fetch protein sequence from UniProt or PDB
- Display protein metadata (title, method, resolution, organism)

3D Protein Visualization
- Interactive 3D viewer using NGL
- Multiple representation styles:(Cartoon,Ball & Stick,Surface)
- Color schemes (chain, element, structure)

Mutation Analysis
- Input mutations (e.g :E6V)
- Displays:
  - Amino acid change
  - Biochemical property change
  - Disease association (demo dataset)
 
Mutation Visualization
- Highlights mutation site in 3D
- Zooms and focuses on affected residue
- Helps understand structural impact

Sequence Analysis
- Displays full protein sequence
- Highlights mutation position
- Amino acid composition chart (Chart.js)

**Project Architecture and Tech Stack**

🔹 Frontend:
- HTML → Structure of the web page  
- CSS → UI Design (Styling)  
- JavaScript → Logic & interaction  
- NGL.js → 3D protein visualization  
- Chart.js → Data visualization (graph)

🔹Backend
- Java (HttpServer) used to create a lightweight REST API

🔹External APIs Used
- **UniProt API** → Fetch protein sequences  
- **RCSB PDB API** → Fetch structure and metadata

⚙️ How to Run the Project:
- Compile Backend:
 javac -d bin src/proteinanalysis/api/*.java
- Run Server:
 java -cp bin proteinanalysis.api.ProteinServer

Run Frontend:
 Open frontend/index.html in browser

Demo Inputs: (Try these examples)

Protein     Hemoglobin  CFTR

ID           P69905    P13569

Mutation     P13569    F508DEL
      
Notes
- Disease mapping is demo-based (hardcoded) for selected mutations
- Supports both UniProt IDs and PDB IDs
- Fully functional end-to-end system

🎯 **Use Case**
- This system helps visualize how mutations affect protein structure, which is useful in:
- Understanding genetic diseases
- Studying protein function
- Supporting drug discovery research

Author

Vyomini

**Future Improvements** :
- Integrate real mutation databases (e.g., ClinVar)
- Predict mutation impact using algorithms
- Add binding site visualization (Drug Discovery)
