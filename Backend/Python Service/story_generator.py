import torch
from transformers import AutoTokenizer, AutoModelForCausalLM
from flask import Flask, request, jsonify

app = Flask(__name__)
device = "cuda" if torch.cuda.is_available() else "cpu"

# Load the model and tokenizer
model_name = "gpt2-medium"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForCausalLM.from_pretrained(model_name).to(device)

# Define endpoint for story generation


@app.route('/generate-story', methods=['POST'])
def generate():
    # Get input prompt from request
    input_prompt = request.get_json().get('input_prompt', '')
    print(input_prompt)
    # Tokenize input text
    input_ids = tokenizer.encode(input_prompt, return_tensors="pt").to(device)
    #input_ids=input_ids['input_ids'].to(device)

    # Set attention mask
    attention_mask = torch.ones_like(input_ids)

    
   
    # Generate story
    output = model.generate(
         input_ids,
        max_length=200,
          min_length=100,  # Increase max_length for longer stories
        num_beams=5,    # Use beam search for better quality
        temperature=0.8,  # Control randomness
        top_p=0.95,      # Use nucleus sampling for diversity
        do_sample=True,  # Enable sampling
        no_repeat_ngram_size=2,
        attention_mask=attention_mask,
        pad_token_id=tokenizer.eos_token_id
    )

    # Decode generated story
   # Check if output is not empty 
    generated_story="No Story Generated"
    if output.shape[0] > 0:
        # Decode generated story
        generated_story = tokenizer.decode(output[0], skip_special_tokens=True)

    return jsonify({'generated_story': generated_story})


if __name__ == '__main__':
    app.run(debug=True)
